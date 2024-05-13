package com.example.fitnessapp.exercises

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fitnessapp.CardGrid
import com.example.fitnessapp.Screen
import com.example.fitnessapp.exercises.musclegroup.MuscleGroup

@Composable
fun ExercisesScreen(
    navController: NavController
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
                val onMuscleGroupClick = { muscleGroupId: String? -> navController.navigate(Screen.Exercises.route + "/" + muscleGroupId) }

                CardGrid(cards = listOf(
                    MuscleGroup.Chest(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) }),
                    MuscleGroup.Shoulders(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) }),
                    MuscleGroup.Biceps(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) }),
                    MuscleGroup.Triceps(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) }),
                    MuscleGroup.Abs(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) }),
                    MuscleGroup.Back(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) }),
                    MuscleGroup.Legs(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) }),
                    MuscleGroup.Calves(onMuscleGroupClick = { muscleGroup -> onMuscleGroupClick(muscleGroup?.id) })
                ))
            }
        }
    )
}