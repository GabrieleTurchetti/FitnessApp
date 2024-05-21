package com.example.fitnessapp

sealed class Screen(val route : String) {
    data object Home : Screen("home")
    data object Map : Screen("map")
    data object Exercises : Screen("exercises")
    data object Profile : Screen("profile")
    data object MuscleGroup : Screen("exercises/{muscleGroupId}")
    data object Exercise : Screen("exercises/{muscleGroupId}/{exerciseId}")
}