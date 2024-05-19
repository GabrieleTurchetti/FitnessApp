package com.example.fitnessapp.utils

fun getBurnedCalories(
    height: Int,
    weight: Int,
    steps: Int
): Int {
    val speed = 1.34
    val MET = 3.5
    val stride = height * 0.414
    val distance = stride * steps
    val time = distance / speed
    val calories = (time * MET * 3.5 * weight / (200 * 60)) / 100
    return calories.toInt()
}