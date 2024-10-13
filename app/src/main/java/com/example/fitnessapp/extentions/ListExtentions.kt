package com.example.fitnessapp.extentions

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

// Calculate the sum of the distances for each LatLng objects in the list
fun List<LatLng>.kilometersTravelled(): Double {
    val earthRadius = 6371
    var kilometersTravelled = 0.0
    var distance: Double

    if (this.size < 2) return 0.0

    for (i in 1..this.size - 1) {
        distance = acos(sin(Math.toRadians(this[i - 1].latitude)) * sin(Math.toRadians(this[i].latitude)) + cos(Math.toRadians(this[i - 1].latitude)) * cos(Math.toRadians(this[i].latitude)) * cos(Math.toRadians(this[i].longitude - this[i - 1].longitude))) * earthRadius
        kilometersTravelled += if (!distance.isNaN()) distance else 0.0
    }
    return kilometersTravelled
}

// Reduce the size of the LatLng list for a better linearity of the graph
fun List<LatLng>.reduceLocations(step: Int): List<LatLng> {
    val partitionedList: List<List<LatLng>> = this.withIndex() 
        .groupBy { it.index / step }
        .map { it.value.map { it.value } }
    val newList = mutableListOf<LatLng>()

    partitionedList.forEach {
        newList.add(LatLng(it.map { location -> location.latitude}.sum() / it.size, it.map { location -> location.longitude}.sum() / it.size))
    }

    return newList
}