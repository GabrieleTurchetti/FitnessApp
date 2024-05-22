package com.example.fitnessapp.home.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.fitnessapp.MainActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.caloriesburned.CaloriesBurnedRepository
import com.example.fitnessapp.location.LocationRepository
import com.example.fitnessapp.stepcounter.StepCounterRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap
import com.google.android.gms.maps.model.SquareCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import java.time.LocalDate

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
@Composable
fun MapScreen(
    date: String?
) {
    var lastLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var locations by remember { mutableStateOf(listOf<LatLng>()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lastLocation, 15f)
    }

    LaunchedEffect(Unit){
        while (true) {
            locations = LocationRepository(MainActivity.db.fitnessDao()).getLocationsByDate(date ?: LocalDate.now().toString())
            cameraPositionState.move(CameraUpdateFactory.newLatLng(locations.last()))
            Log.d("LIST", "${locations.size}, $date")
            delay(1000)
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Polyline(
            points = locations,
            color = colorResource(R.color.purple_200)
        )
    }
}