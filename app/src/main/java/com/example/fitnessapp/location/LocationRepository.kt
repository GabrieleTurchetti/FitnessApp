package com.example.fitnessapp.location

import com.example.fitnessapp.extentions.reduceLocations
import com.example.fitnessapp.room.Location
import com.example.fitnessapp.extentions.round
import com.example.fitnessapp.room.FitnessDatabase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class LocationRepository {
    companion object {
        val fitnessDao = FitnessDatabase.db.fitnessDao()
        suspend fun insertLocation(location: LatLng) = withContext(Dispatchers.IO) {
            val newLocation = Location(
                date = LocalDate.now().toString(),
                latitude = location.latitude.toFloat(),
                longitude = location.longitude.toFloat()
            )
            fitnessDao.insertLocation(newLocation)
        }

        suspend fun getLocationsByDate(date: String = LocalDate.now().toString()): List<LatLng> =
            withContext(Dispatchers.IO) {
                val dateLocations = fitnessDao.getLocationsByDate(date = date)

                if (dateLocations.isEmpty()) {
                    emptyList()
                } else {
                    dateLocations.map { location ->
                        LatLng(
                            location.latitude.toDouble().round(4, 0.5f),
                            location.longitude.toDouble().round(4, 0.5f)
                        )
                    }.reduceLocations(5)
                }
            }

        suspend fun getLastLocation(date: String = LocalDate.now().toString()): LatLng =
            withContext(Dispatchers.IO) {
                val dateLocations = fitnessDao.getLocationsByDate(date = date)

                if (dateLocations.isEmpty()) {
                    LatLng(0.0, 0.0)
                } else {
                    val lastLocation = dateLocations.last()
                    LatLng(
                        lastLocation.latitude.toDouble().round(4, 0.5f),
                        lastLocation.longitude.toDouble().round(4, 0.5f)
                    )
                }
            }
    }
}