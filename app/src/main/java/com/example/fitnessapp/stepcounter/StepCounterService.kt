package com.example.fitnessapp.stepcounter

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.datastore.ProfileSettings
import com.example.fitnessapp.extentions.round
import com.example.fitnessapp.location.LocationClient
import com.example.fitnessapp.location.LocationRepository
import com.example.fitnessapp.stepcounter.calories.CaloriesRepository
import com.example.fitnessapp.stepcounter.steps.StepsRepository
import com.example.fitnessapp.utils.getBurnedCalories
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
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

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
class StepCounterService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var stepCounterClient: StepCounterClient

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
        val notification = NotificationCompat.Builder(this, "fitness")
            .setContentTitle("Tracciamento passi attivo")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.d("TAGS", "---")

        stepCounterClient
            .getStepCounterUpdates(1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { stepsSinceLastReboot ->

                scope.launch {
                    val todaySteps = StepsRepository(MainActivity.db.fitnessDao()).getStepsByDate(
                        LocalDate.now().toString())
                    val todayStepsAtLastReboot = StepsRepository(MainActivity.db.fitnessDao()).getTodayStepsAtLastReboot()
                    val initialSteps = StepsRepository(MainActivity.db.fitnessDao()).getInitialStepsByDate(
                        LocalDate.now().toString())
                    val dataStore = ProfileSettings(applicationContext)

                    launch {
                        dataStore.getHeightAndWeight.cancellable().collect {
                            Log.d("PROVA", "$stepsSinceLastReboot - $this")
                            val height = it.first
                            val weight = it.second

                            CaloriesRepository(MainActivity.db.fitnessDao()).insertCalories(
                                getBurnedCalories(height, weight, todaySteps)
                            )
                        }
                    }

                    var currentTodaySteps = 0

                    if (stepsSinceLastReboot <= todaySteps) {
                        if (stepsSinceLastReboot == 0) {
                            currentTodaySteps = todaySteps
                            StepsRepository(MainActivity.db.fitnessDao()).insertSteps(
                                currentTodaySteps,
                                currentTodaySteps,
                                initialSteps
                            )
                        }
                        else {
                            currentTodaySteps = stepsSinceLastReboot + todayStepsAtLastReboot
                            StepsRepository(MainActivity.db.fitnessDao()).insertSteps(
                                currentTodaySteps,
                                todayStepsAtLastReboot,
                                initialSteps
                            )
                        }
                    } else {
                        if (todaySteps == -1) {
                            StepsRepository(MainActivity.db.fitnessDao()).insertSteps(
                                0,
                                0,
                                stepsSinceLastReboot
                            )
                        }
                        else {
                            currentTodaySteps = stepsSinceLastReboot - initialSteps
                            StepsRepository(MainActivity.db.fitnessDao()).insertSteps(
                                currentTodaySteps,
                                currentTodaySteps,
                                initialSteps
                            )
                        }
                    }

                    launch {
                        dataStore.getStepGoal.cancellable().collect {
                            if (currentTodaySteps >= it) {
                                val notification = NotificationCompat.Builder(applicationContext, "fitness")
                                    .setContentTitle("Obiettivo passi raggiunto!")
                                    .setContentText("Passi: $currentTodaySteps / $it")
                                    .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                                notificationManager.notify(3, notification.build())
                            }
                        }
                    }
                }
            }.launchIn(serviceScope)

        startForeground(2, notification.build())
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