package com.example.fitnessapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessapp.datastore.ProfileSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDate

private const val TAG = "STEP_COUNT_LISTENER"

class StepCounter(
    val context: Context
) {
    val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val sensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) }
    val scope = CoroutineScope(Dispatchers.IO)

    suspend fun steps() = suspendCancellableCoroutine { continuation ->
        Log.d(TAG, "Registering sensor listener... ")

        val listener: SensorEventListener by lazy {
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return

                    val stepsSinceLastReboot = event.values[0].toInt()
                    Log.d(TAG, "Steps since last reboot: $stepsSinceLastReboot")

                    scope.launch {
                        val todaySteps = StepCounterRepository(MainActivity.db.stepsDao()).getStepsByDate(LocalDate.now().toString())
                        val stepsAtLastReboot = StepCounterRepository(MainActivity.db.stepsDao()).getStepsAtLastReboot()

                        if (stepsSinceLastReboot <= todaySteps) {
                            if (stepsSinceLastReboot == 0) StepCounterRepository(MainActivity.db.stepsDao()).storeSteps(todaySteps, todaySteps)
                            else StepCounterRepository(MainActivity.db.stepsDao()).storeSteps(stepsSinceLastReboot + stepsAtLastReboot, stepsAtLastReboot)
                        }
                        else {
                            val yesterdaySteps = StepCounterRepository(MainActivity.db.stepsDao()).getStepsByDate(LocalDate.now().minusDays(1).toString())
                            StepCounterRepository(MainActivity.db.stepsDao()).storeSteps(stepsSinceLastReboot - yesterdaySteps, stepsAtLastReboot)
                        }
                    }

                    if (continuation.isActive) {
                        continuation.resume(stepsSinceLastReboot, {})
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    Log.d(TAG, "Accuracy changed to: $accuracy")
                }
            }
        }

        val supportedAndEnabled = sensorManager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_UI
        )
        Log.d(TAG, "Sensor listener registered: $supportedAndEnabled")
    }
}