package com.example.fitnessapp.stepcounter

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.example.fitnessapp.extentions.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class StepCounterClient(
    private val context: Context
) {
    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    val scope = CoroutineScope(Dispatchers.IO)

    @SuppressLint("MissingPermission")
    fun getStepCounterUpdates(interval: Long): Flow<Int> {
        Log.d("TAGS", "1")
        return callbackFlow {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            Log.d("TAGS", "2")

            val sensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return
                    val stepsSinceLastReboot = event.values[0].toInt()
                    Log.d("TAGS", "$stepsSinceLastReboot")

                    scope.launch {
                        Log.d("TAGS", "PIPPO")
                        send(stepsSinceLastReboot)
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    Log.d("PIP", "Accuracy changed to: $accuracy")
                }
            }

            sensorManager.registerListener(
                sensorEventListener,
                stepCounterSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )

            awaitClose {
                sensorManager.unregisterListener(sensorEventListener)
            }
        }
    }

    class LocationException(message: String): Exception()
}