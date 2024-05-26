package com.example.fitnessapp.home.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.fitnessapp.R
import com.example.fitnessapp.location.LocationRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import java.time.LocalDate

@Composable
fun MapScreen(
    date: String?
) {
    val lastLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var locations by remember { mutableStateOf(listOf<LatLng>()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lastLocation, 15f)
    }

    LaunchedEffect(Unit){
        while (true) {
            val newLocations = LocationRepository.getLocationsByDate(date ?: LocalDate.now().toString())

            if (!newLocations.isEmpty() && newLocations.size != locations.size)  {
                cameraPositionState.move(CameraUpdateFactory.newLatLng(newLocations.last()))
                locations = newLocations
            }
            
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