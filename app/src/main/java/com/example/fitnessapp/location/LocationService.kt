package com.example.fitnessapp.location

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.fitnessapp.extentions.round
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

class LocationService : Service() {
    val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val scope = CoroutineScope(Dispatchers.IO)
    lateinit var locationClient: LocationClient

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
        val locationTrackingNotification = NotificationCompat.Builder(this, "fitness")
            .setContentTitle("Tracciamento percorso attivo")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)

        locationClient
            .getLocationUpdates(1000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                scope.launch {
                    val lastLocation = LocationRepository.getLastLocation()
                    val currentLocation = LatLng(location.latitude.round(4, 0.5f), location.longitude.round(4, 0.5f))
                    if (lastLocation != currentLocation) LocationRepository.insertLocation(currentLocation)
                }
            }.launchIn(serviceScope)

        startForeground(1, locationTrackingNotification.build())
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