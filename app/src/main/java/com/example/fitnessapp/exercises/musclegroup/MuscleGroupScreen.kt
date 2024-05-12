package com.example.fitnessapp.exercises.musclegroup

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fitnessapp.CardGrid
import com.example.fitnessapp.Screen
import com.example.fitnessapp.exercises.ExercisesTopAppBar
import com.example.fitnessapp.exercises.MuscleGroup

@Composable
fun MuscleGroupScreen(
    navController: NavController,
    muscleGroupId: String?
) {
    Scaffold(
        topBar = {
            ExercisesTopAppBar()
        },
        content = { padding ->
            Surface(
                modifier = Modifier.padding(padding),
                color = MaterialTheme.colorScheme.background
            ) {
                val onExerciseClick = { muscleGroupId: String -> navController.navigate(Screen.MuscleGroup.route + muscleGroupId) }
                CardGrid(cards = MuscleGroup.getClassFromId(muscleGroupId))
            }
        }
    )
}

