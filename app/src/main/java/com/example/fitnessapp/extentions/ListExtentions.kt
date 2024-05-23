package com.example.fitnessapp.extentions

import com.google.android.gms.maps.model.LatLng
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

fun List<LatLng>.kilometersTravelled(): Double {
    val earthRadius = 6371
    var metersTravelled = 0.0

    if (this.size < 2) return 0.0

    for (i in 1..this.size - 1) {
        metersTravelled += acos(sin(this[i - 1].latitude) * sin(this[i].latitude) + cos(this[i - 1].latitude) * cos(this[i].latitude) * cos(this[i].longitude - this[i - 1].longitude)) * earthRadius
    }

    return metersTravelled.toInt() / 1000.0
}