package com.example.fitnessapp.exercises

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fitnessapp.ui.common.CardGrid
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
                // Route to the section of the muscle group
                val onMuscleGroupClick = { muscleGroupId: String -> navController.navigate(Screen.Exercises.route + "/" + muscleGroupId) }

                CardGrid(cards = listOf(
                    MuscleGroup.Chest(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Chest().id) }),
                    MuscleGroup.Shoulders(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Shoulders().id) }),
                    MuscleGroup.Biceps(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Biceps().id) }),
                    MuscleGroup.Triceps(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Triceps().id) }),
                    MuscleGroup.Abs(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Abs().id) }),
                    MuscleGroup.Back(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Back().id) }),
                    MuscleGroup.Legs(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Legs().id) }),
                    MuscleGroup.Calves(onMuscleGroupClick = { onMuscleGroupClick(MuscleGroup.Calves().id) })
                ))
            }
        }
    )
}