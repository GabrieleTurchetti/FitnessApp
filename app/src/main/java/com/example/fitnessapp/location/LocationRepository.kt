package com.example.fitnessapp.location

import android.util.Log
import com.example.fitnessapp.room.FitnessDao
import com.example.fitnessapp.room.Location
import com.example.fitnessapp.room.Steps
import com.example.fitnessapp.utils.round
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate

class LocationRepository(
    private val fitnessDao: FitnessDao,
) {
    suspend fun insertLocation(location: LatLng) = withContext(Dispatchers.IO) {
        val location = Location(
            date = LocalDate.now().toString(),
            latitude = location.latitude.toFloat(),
            longitude = location.longitude.toFloat()
        )
        Log.d("LOCATION", "${location.latitude} + ${location.longitude}")
        fitnessDao.insertLocation(location)
    }

    suspend fun getLocationsByDate(date: String = LocalDate.now().toString()): List<LatLng> = withContext(Dispatchers.IO) {
        val dateLocations = fitnessDao.getLocationsByDate(date = date)

        if (dateLocations.isEmpty()) {
            emptyList()
        }
        else {
            dateLocations.map { location ->
                LatLng(location.latitude.toDouble().round(4), location.longitude.toDouble().round(4))
            }
        }
    }

    suspend fun getLastLocation(date: String = LocalDate.now().toString()): LatLng = withContext(Dispatchers.IO) {
        val dateLocations = fitnessDao.getLocationsByDate(date = date)

        if (dateLocations.isEmpty()) {
            LatLng(0.0, 0.0)
        }
        else {
            val lastLocation = dateLocations.last()
            LatLng(lastLocation.latitude.toDouble().round(4), lastLocation.longitude.toDouble().round(4))
        }
    }
}