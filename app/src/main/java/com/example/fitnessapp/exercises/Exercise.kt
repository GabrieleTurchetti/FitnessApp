package com.example.fitnessapp.exercises

import androidx.annotation.DrawableRes

data class Exercise(
    val name: String,
    @DrawableRes val gifId: Int
)