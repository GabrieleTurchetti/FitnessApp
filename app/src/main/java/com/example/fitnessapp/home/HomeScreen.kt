package com.example.fitnessapp.home

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.content.Intent
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessapp.stepcounter.StepCounterViewModel
import com.example.fitnessapp.datastore.ProfileSettings
import com.example.fitnessapp.location.LocationService
import com.example.fitnessapp.utils.getBurnedCalories
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    navController: NavController,
    stepCounterViewModel: StepCounterViewModel = viewModel()
) {
    val context = LocalContext.current
    val dataStore = ProfileSettings(context)
    var steps by remember { mutableStateOf(0) }
    val savedStepGoal = dataStore.getStepGoal.collectAsState(initial = "0")
    val savedHeight = dataStore.getHeight.collectAsState(initial = "0")
    val savedWeight = dataStore.getWeight.collectAsState(initial = "0")
    val showDatePicker = remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    val lifecycleOwner = LocalLifecycleOwner.current
    var isActivityRecognitionGranted by remember { mutableStateOf(false) }
    var isAccessCoarseLocaltionGranted by remember { mutableStateOf(false) }
    var isAccessFineLocationGranted by remember { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            ACTIVITY_RECOGNITION,
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        )
    )

    DisposableEffect(lifecycleOwner, isActivityRecognitionGranted) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> if (isActivityRecognitionGranted) {stepCounterViewModel.registerListenerStepCounter(context); Log.d("TAG", "isgrant")}
                Lifecycle.Event.ON_PAUSE -> {stepCounterViewModel.unregisterListenerStepCounter(context); Log.d("TAG", "isnot")}
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(permissionsState) {
        permissionsState.launchMultiplePermissionRequest()

        permissionsState.permissions.forEach {
            when (it.permission) {
                ACTIVITY_RECOGNITION -> {
                    if (it.status.isGranted) {
                        isActivityRecognitionGranted = true
                        stepCounterViewModel.init(context)
                    }
                }
                ACCESS_COARSE_LOCATION -> {
                    if (it.status.isGranted) isAccessCoarseLocaltionGranted = true
                }
                ACCESS_FINE_LOCATION -> {
                    if (it.status.isGranted) isAccessFineLocationGranted = true
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(date){
        while (true) {
            steps = stepCounterViewModel.getSteps(date)
            if (steps == -1) steps = 0
            delay(1000)
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                showDatePicker,
                navController
            )
        },
        content = { padding ->
            val datePickerState = rememberDatePickerState()

            Surface(
                modifier = Modifier.padding(padding),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(2f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressBar(steps = steps, stepsGoal = savedStepGoal.value!!.toInt())
                        Button(onClick = {
                            Intent(context, LocationService::class.java).apply {
                                action = LocationService.ACTION_START
                                context.startService(this)
                            }
                        }) {
                            Text(text = "Start")
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                            ) {
                                Text("Passi:")
                                Text("$steps")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                            ) {
                                Text("Kilometri:")
                                Text("1 km")
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(1f),
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                            )  {
                                Text("Calorie:")
                                Text("${getBurnedCalories(savedHeight.value!!.toInt(), savedWeight.value!!.toInt(), steps)} kcal")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                            ) {
                                Text("Giorno:")
                                Text(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                            }
                        }
                    }
                }
            }

            if (showDatePicker.value) {
                DatePickerDialog(
                    onDismissRequest = { /*TODO*/ },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (datePickerState.selectedDateMillis != null) {
                                    val selectedDate = Calendar.getInstance().apply {
                                        timeInMillis = datePickerState.selectedDateMillis!!
                                    }
                                    if (selectedDate.before(Calendar.getInstance())) {
                                        val format1 = SimpleDateFormat("yyyy-MM-dd")
                                        date = format1.format(selectedDate.time)
                                        showDatePicker.value = false
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "La data selezionta non è valida",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                else Toast.makeText(
                                    context,
                                    "La data selezionta non è valida",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDatePicker.value = false
                            }
                        ) { Text("Cancel") }
                    }
                )
                {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }
        }
    )
}


@Composable
private fun CircularProgressBar(
    size: Dp = 200.dp,
    strokeWidth: Dp = 30.dp,
    backgroundArcColor: Color = MaterialTheme.colorScheme.secondary,
    steps: Int,
    stepsGoal: Int
) {
    CircularProgressIndicator(
        modifier = Modifier.size(size),
        strokeWidth = strokeWidth,
        strokeCap = StrokeCap.Round,
        color = backgroundArcColor,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        progress = if (stepsGoal != 0) steps.toFloat() / stepsGoal else 0f
    )
    Text(
        style = LocalTextStyle.current.copy(fontSize = 25.sp),
        text = DecimalFormat("#,###")
            .format(steps)
            .replace(",", ".")
    )
}