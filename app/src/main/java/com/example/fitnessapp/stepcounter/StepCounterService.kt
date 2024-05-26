package com.example.fitnessapp.stepcounter

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import com.example.fitnessapp.datastore.ProfileSettings
import com.example.fitnessapp.stepcounter.calories.CaloriesRepository
import com.example.fitnessapp.stepcounter.steps.StepsRepository
import com.example.fitnessapp.utils.getBurnedCalories
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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
    val scope = CoroutineScope(Dispatchers.IO)
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
                scope.launch {
                    val todaySteps = StepsRepository.getStepsByDate(LocalDate.now().toString())
                    val todayStepsAtLastReboot = StepsRepository.getTodayStepsAtLastReboot()
                    val initialSteps = StepsRepository.getInitialStepsByDate(LocalDate.now().toString())
                    val dataStore = ProfileSettings(applicationContext)

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

                    if (stepsSinceLastReboot <= todaySteps) {
                        if (stepsSinceLastReboot == 0) {
                            currentTodaySteps = todaySteps
                            StepsRepository.insertSteps(
                                currentTodaySteps,
                                currentTodaySteps,
                                initialSteps
                            )
                        }
                        else {
                            currentTodaySteps = stepsSinceLastReboot + todayStepsAtLastReboot
                            StepsRepository.insertSteps(
                                currentTodaySteps,
                                todayStepsAtLastReboot,
                                initialSteps
                            )
                        }
                    } else {
                        if (todaySteps == 0) {
                            StepsRepository.insertSteps(
                                0,
                                0,
                                stepsSinceLastReboot
                            )
                        }
                        else {
                            currentTodaySteps = stepsSinceLastReboot - initialSteps
                            StepsRepository.insertSteps(
                                currentTodaySteps,
                                currentTodaySteps,
                                initialSteps
                            )
                        }
                    }

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