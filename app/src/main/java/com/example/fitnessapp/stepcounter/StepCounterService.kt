package com.example.fitnessapp.stepcounter

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.fitnessapp.datastore.ProfileSettings
import com.example.fitnessapp.stepcounter.calories.CaloriesRepository
import com.example.fitnessapp.stepcounter.steps.StepsRepository
import com.example.fitnessapp.utils.getBurnedCalories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class StepCounterService : Service() {
    val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    lateinit var stepCounterClient: StepCounterClient

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        stepCounterClient = StepCounterClient(
            applicationContext
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        // Notify that the steps are being tracked
        val stepTrackingNotification = NotificationCompat.Builder(this, "fitness")
            .setContentTitle("Tracciamento passi attivo")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        stepCounterClient
            .getStepCounterUpdates()
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { stepsSinceLastReboot ->
                // When a new value of steps is retried then it's saved in the database
                serviceScope.launch {
                    val todaySteps = StepsRepository.getStepsByDate(LocalDate.now().toString())
                    val todayStepsAtLastReboot = StepsRepository.getTodayStepsAtLastReboot()
                    val initialSteps = StepsRepository.getInitialStepsByDate(LocalDate.now().toString())
                    val dataStore = ProfileSettings(applicationContext)

                    // Based on the value of steps the updated value of calories burned is calculate and then saved in the database
                    launch {
                        dataStore.getHeightAndWeight.cancellable().collect {
                            val height = it.first
                            val weight = it.second

                            CaloriesRepository.insertCalories(
                                getBurnedCalories(height, weight, todaySteps)
                            )
                        }
                    }

                    var currentTodaySteps = 0

                    // If the device has been rebooted today
                    if (stepsSinceLastReboot <= todaySteps) {
                        // If the device has just been rebooted
                        if (stepsSinceLastReboot == 0) {
                            currentTodaySteps = todaySteps
                            StepsRepository.insertSteps(
                                currentTodaySteps,
                                currentTodaySteps,
                                initialSteps
                            )
                        }
                        // If the device has been rebooted today but not now
                        else {
                            currentTodaySteps = stepsSinceLastReboot + todayStepsAtLastReboot
                            StepsRepository.insertSteps(
                                currentTodaySteps,
                                todayStepsAtLastReboot,
                                initialSteps
                            )
                        }
                    }
                    // If the device has been rebooted before today
                    else {
                        // When a new day has just started
                        if (todaySteps == -1) {
                            StepsRepository.insertSteps(
                                0,
                                0,
                                stepsSinceLastReboot
                            )
                        }
                        // During the rest of the day
                        else {
                            currentTodaySteps = stepsSinceLastReboot - initialSteps
                            StepsRepository.insertSteps(
                                currentTodaySteps,
                                currentTodaySteps,
                                initialSteps
                            )
                        }
                    }

                    // Send a notification when the user reaches its daily step goal
                    launch {
                        dataStore.getStepGoal.cancellable().collect {
                            if (currentTodaySteps >= it) {
                                val stepGoalNotification = NotificationCompat.Builder(applicationContext, "fitness")
                                    .setContentTitle("Obiettivo passi raggiunto!")
                                    .setContentText("Passi: $currentTodaySteps/$it")
                                    .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                                notificationManager.notify(3, stepGoalNotification.build())
                            }
                        }
                    }
                }
            }.launchIn(serviceScope)

        // Start the foreground step counter service
        startForeground(2, stepTrackingNotification.build())
    }

    private fun stop() {
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}