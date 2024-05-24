package com.example.fitnessapp

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.fitnessapp.bottomnavigationbar.BottomNavigationBar
import com.example.fitnessapp.location.LocationService
import com.example.fitnessapp.room.AppDatabase
import com.example.fitnessapp.stepcounter.StepCounterService
import com.example.fitnessapp.stepcounter.StepCounterViewModel
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "fitness"
        ).build()

        setContent {
            FitnessAppTheme {
                val stepCounterViewModel: StepCounterViewModel by viewModels()
                val context = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                var isActivityRecognitionGranted by remember { mutableStateOf(false) }
                var isAccessCoarseLocationGranted by remember { mutableStateOf(false) }
                var isAccessFineLocationGranted by remember { mutableStateOf(false) }
                var isPostNotificationGranted by remember { mutableStateOf(false) }
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
                                isActivityRecognitionGranted = true
                                Intent(context, StepCounterService::class.java).apply {
                                    action = StepCounterService.ACTION_START
                                    context.startService(this)
                                }
                            }
                        }
                        Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            if (it.status.isGranted) isAccessCoarseLocationGranted = true
                        }
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            if (it.status.isGranted) {
                                isAccessFineLocationGranted = true
                                Intent(context, LocationService::class.java).apply {
                                    action = LocationService.ACTION_START
                                    context.startService(this)
                                }
                            }
                        }
                        Manifest.permission.POST_NOTIFICATIONS -> {
                            if (it.status.isGranted) isPostNotificationGranted = true
                        }
                        else -> {}
                    }
                }

                BottomNavigationBar()
            }
        }
    }
}