package com.example.fitnessapp.stepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.burnedcalories.BurnedCaloriesRepository
import com.example.fitnessapp.datastore.ProfileSettings
import com.example.fitnessapp.utils.getBurnedCalories
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.reflect.TypeVariable
import java.time.LocalDate


private const val TAG = "STEP_COUNT_LISTENER"

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
class StepCounter(
    val context: Context
) {
    val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val sensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) }
    val scope = CoroutineScope(Dispatchers.IO)
    var listener: SensorEventListener? = null

    fun steps() {
        Log.d(TAG, "Registering sensor listener... ")

        listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                val stepsSinceLastReboot = event.values[0].toInt()
                Log.d(TAG, "Steps since last reboot: $stepsSinceLastReboot")

                Log.d("PIPPOZ1", "$stepsSinceLastReboot")

                scope.launch {
                    val todaySteps = StepCounterRepository(MainActivity.db.fitnessDao()).getStepsByDate(LocalDate.now().toString())
                    val todayStepsAtLastReboot = StepCounterRepository(MainActivity.db.fitnessDao()).getTodayStepsAtLastReboot()
                    val initialSteps = StepCounterRepository(MainActivity.db.fitnessDao()).getInitialStepsByDate(LocalDate.now().toString())
                    val dataStore = ProfileSettings(context)

                    launch {
                        dataStore.getHeightAndWeight.cancellable().collect {
                            Log.d("PROVA", "$stepsSinceLastReboot - $this")
                            val height = it.first
                            val weight = it.second

                            BurnedCaloriesRepository(MainActivity.db.fitnessDao()).insertCalories(
                                getBurnedCalories(height, weight, todaySteps)
                            )
                        }
                    }

                    Log.d("PIPPOZ", "$stepsSinceLastReboot")

                    if (stepsSinceLastReboot <= todaySteps) { // il reboot è stato fatto oggi
                        if (stepsSinceLastReboot == 0) {
                            StepCounterRepository(MainActivity.db.fitnessDao()).insertSteps(
                                todaySteps,
                                todaySteps,
                                initialSteps
                            )
                        }
                        else {
                            StepCounterRepository(MainActivity.db.fitnessDao()).insertSteps(
                                stepsSinceLastReboot + todayStepsAtLastReboot,
                                todayStepsAtLastReboot,
                                initialSteps
                            )
                        }
                    } else { // il reboot è stato fatto prima di oggi
                        if (todaySteps == -1) {
                            StepCounterRepository(MainActivity.db.fitnessDao()).insertSteps(
                                0,
                                0,
                                stepsSinceLastReboot
                            )
                        }
                        else {
                            StepCounterRepository(MainActivity.db.fitnessDao()).insertSteps(
                                stepsSinceLastReboot - initialSteps,
                                stepsSinceLastReboot - initialSteps,
                                initialSteps
                            )
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.d(TAG, "Accuracy changed to: $accuracy")
            }
        }
    }

    fun registerListener() {
        if (listener != null) {
            val supportedAndEnabled = sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )

            Log.d(TAG, "Sensor listener registered: $supportedAndEnabled")
        }
    }

    fun unregisterListener() {
        if (listener != null) {
            sensorManager.unregisterListener(listener)
        }
    }
}