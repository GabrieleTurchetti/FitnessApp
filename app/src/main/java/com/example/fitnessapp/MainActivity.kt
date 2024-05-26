package com.example.fitnessapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.fitnessapp.bottomnavigationbar.BottomNavigationBar
import com.example.fitnessapp.location.LocationService
import com.example.fitnessapp.room.FitnessDatabase
import com.example.fitnessapp.stepcounter.StepCounterService
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channel = NotificationChannel(
            "fitness",
            "Fitness",
            NotificationManager.IMPORTANCE_LOW
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        FitnessDatabase.init(applicationContext)

        setContent {
            FitnessAppTheme {
                val context = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.ACTIVITY_RECOGNITION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                )

                DisposableEffect(lifecycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_START) {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }

                permissionsState.permissions.forEach {
                    when (it.permission) {
                        Manifest.permission.ACTIVITY_RECOGNITION -> {
                            if (it.status.isGranted) {
                                Intent(context, StepCounterService::class.java).apply {
                                    action = StepCounterService.ACTION_START
                                    context.startService(this)
                                }
                            }
                        }
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            if (it.status.isGranted) {
                                Intent(context, LocationService::class.java).apply {
                                    action = LocationService.ACTION_START
                                    context.startService(this)
                                }
                            }
                        }
                        else -> {}
                    }
                }

                BottomNavigationBar()
            }
        }
    }
}