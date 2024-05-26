package com.example.fitnessapp.stepcounter

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.fitnessapp.extentions.hasActivityRecognitionPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class StepCounterClient(
    val context: Context
) {
    var sensorManager: SensorManager? = null
    var stepCounterSensor: Sensor? = null
    val scope = CoroutineScope(Dispatchers.IO)

    @SuppressLint("MissingPermission")
    fun getStepCounterUpdates(): Flow<Int> {
        return callbackFlow {
            if (!context.hasActivityRecognitionPermission()) {
                throw StepCounterExeption("Missing activity recognition permission")
            }

            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            stepCounterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

            val sensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return
                    val stepsSinceLastReboot = event.values[0].toInt()

                    scope.launch {
                        send(stepsSinceLastReboot)
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager?.registerListener(
                sensorEventListener,
                stepCounterSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )

            awaitClose {
                sensorManager?.unregisterListener(sensorEventListener)
            }
        }
    }

    class StepCounterExeption(message: String): Exception(message)
}