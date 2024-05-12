package com.example.fitnessapp

sealed class Screen(val route : String) {
    data object Home : Screen("home")
    data object Exercises : Screen("exercises")
    data object Profile : Screen("profile")
    data object MuscleGroup : Screen("exercises/{muscleGroupId}")
}