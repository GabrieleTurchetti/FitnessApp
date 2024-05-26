package com.example.fitnessapp.extentions

import kotlin.math.round

fun Double.round(decimals: Int, accuracy: Float): Double {
    var multiplier = 1.0

    repeat(decimals) {
        multiplier *= 10
    }

    return round(this * multiplier * accuracy) / (multiplier * accuracy)
}