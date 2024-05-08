package com.example.fitnessapp

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Exercises : Screens("exercises_route")
    object Profile : Screens("profile_route")
}