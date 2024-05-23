package com.example.fitnessapp.home

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.burnedcalories.BurnedCaloriesRepository
import com.example.fitnessapp.datastore.ProfileSettings
import com.example.fitnessapp.extentions.kilometersTravelled
import com.example.fitnessapp.location.LocationRepository
import com.example.fitnessapp.stepcounter.StepCounterRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val dataStore = ProfileSettings(context)
    var steps by remember { mutableStateOf(0) }
    val stepGoal = dataStore.getStepGoal.collectAsState(initial = 0)
    val showDatePicker = remember { mutableStateOf(false) }
    var calories by remember { mutableStateOf(0) }
    var date = remember { mutableStateOf(LocalDate.now().toString()) }
    var kilometersTravelled by remember { mutableStateOf(0.0) }

    LaunchedEffect(date){
        while (true) {
            steps = StepCounterRepository(MainActivity.db.fitnessDao()).getStepsByDate(date.value)
            calories = BurnedCaloriesRepository(MainActivity.db.fitnessDao()).getCaloriesByDate(date.value)
            if (steps == -1) steps = 0
            if (calories == -1) calories = 0
            delay(1000)
        }
    }

    LaunchedEffect(date){
        while (true) {
            kilometersTravelled = LocationRepository(MainActivity.db.fitnessDao()).getLocationsByDate(date.value).kilometersTravelled()
            delay(1000)
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                showDatePicker,
                navController,
                date
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
                        CircularProgressBar(steps = steps, stepsGoal = stepGoal.value)
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
                                Text("$kilometersTravelled km")
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
                                Text("$calories kcal")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                            ) {
                                Text("Giorno:")
                                Text(LocalDate.parse(date.value, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
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
                                        date.value = format1.format(selectedDate.time)
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