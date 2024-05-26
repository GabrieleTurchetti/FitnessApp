package com.example.fitnessapp.bottomnavigationbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.fitnessapp.R
import com.example.fitnessapp.Screen

data class BottomNavigationItem(
    val label : String,
    val icon : ImageVector,
    val route : String
) {
    companion object {
        @Composable
        fun bottomNavigationItems() : List<BottomNavigationItem> {
            return listOf(
                BottomNavigationItem(
                    label = "Esercizi",
                    icon = ImageVector.vectorResource(R.drawable.ic_fitness_center),
                    route = Screen.Exercises.route
                ),
                BottomNavigationItem(
                    label = "Home",
                    icon = Icons.Filled.Home,
                    route = Screen.Home.route
                ),
                BottomNavigationItem(
                    label = "Profilo",
                    icon = Icons.Filled.AccountCircle,
                    route = Screen.Profile.route
                )
            )
        }
    }
}
