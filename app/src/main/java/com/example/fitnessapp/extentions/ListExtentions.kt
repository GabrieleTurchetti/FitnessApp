package com.example.fitnessapp.extentions

import com.google.android.gms.maps.model.LatLng
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

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

fun List<LatLng>.reduceLocations(step: Int): List<LatLng> {
    return this.slice(0..this.size - 1 step step)
}