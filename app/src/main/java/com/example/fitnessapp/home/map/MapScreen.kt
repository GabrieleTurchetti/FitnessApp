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
    // List of locations that will be used as polyline points on the map
    var locations by remember { mutableStateOf(listOf<LatLng>()) }
    val initialLocation = LatLng(0.0, 0.0)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(initialLocation, 15f, 0f, 0f)
    }

    LaunchedEffect(Unit){
        while (true) {
            val newLocations = LocationRepository.getLocationsByDate(date ?: LocalDate.now().toString())

            // Update the camera position if the location change
            if (newLocations.isNotEmpty() && newLocations.size != locations.size)  {
                cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition(newLocations.last(), 15f, 0f, 0f)))
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