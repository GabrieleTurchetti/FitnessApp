package com.example.fitnessapp.location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.extentions.round
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
class LocationService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = LocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
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
            .setContentTitle("Tracciamento percorso attivo")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)

        locationClient
            .getLocationUpdates(1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                scope.launch {
                    val lastLocation = LocationRepository(MainActivity.db.fitnessDao()).getLastLocation()
                    val currentLocation = LatLng(location.latitude.round(4, 0.5f), location.longitude.round(4, 0.5f))
                    Log.d("LOC", "Location: (${currentLocation.latitude}, ${currentLocation.longitude})")

                    if (lastLocation != currentLocation) {
                        Log.d("LOC", "Location: (${lastLocation.latitude}, ${lastLocation.longitude}), (${currentLocation.latitude}, ${currentLocation.longitude})")
                        LocationRepository(MainActivity.db.fitnessDao()).insertLocation(currentLocation)
                    }
                }
            }.launchIn(serviceScope)

        startForeground(1, notification.build())
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