package com.example.fitnessapp

sealed class Screen(val route : String) {
    object Home : Screen("home_route")
    object Exercises : Screen("exercises_route")
    object Profile : Screen("profile_route")
}