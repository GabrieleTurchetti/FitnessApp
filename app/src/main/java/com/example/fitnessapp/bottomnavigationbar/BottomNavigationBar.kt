package com.example.fitnessapp.bottomnavigationbar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitnessapp.Screen
import com.example.fitnessapp.exercises.ExercisesScreen
import com.example.fitnessapp.exercises.musclegroup.exercise.ExerciseScreen
import com.example.fitnessapp.exercises.musclegroup.MuscleGroupScreen
import com.example.fitnessapp.home.HomeScreen
import com.example.fitnessapp.profile.ProfileScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
@Composable
fun BottomNavigationBar() {
    var navigationSelectedItem by remember {
        mutableIntStateOf(1)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index

                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screen.Exercises.route) {
                ExercisesScreen(
                    navController
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navController
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController
                )
            }
            composable(
                Screen.MuscleGroup.route,
                arguments = listOf(navArgument("muscleGroupId") { type = NavType.StringType })
            ) { backStackEntry ->
                MuscleGroupScreen(
                    navController,
                    backStackEntry.arguments?.getString("muscleGroupId"),
                )
            }
            composable(
                Screen.Exercise.route,
                arguments = listOf(
                    navArgument("muscleGroupId") { type = NavType.StringType },
                    navArgument("exerciseId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                ExerciseScreen(
                    navController,
                    backStackEntry.arguments?.getString("muscleGroupId"),
                    backStackEntry.arguments?.getString("exerciseId")
                )
            }
        }
    }
}
