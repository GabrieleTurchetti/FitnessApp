package com.example.fitnessapp.exercises.musclegroup

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fitnessapp.ui.common.CardGrid
import com.example.fitnessapp.Screen

@Composable
fun MuscleGroupScreen(
    navController: NavController,
    muscleGroupId: String?
) {
    Scaffold(
        topBar = {
            MuscleGroupTopAppBar(MuscleGroup.getNameFromId(muscleGroupId))
        },
        content = { padding ->
            Surface(
                modifier = Modifier.padding(padding),
                color = MaterialTheme.colorScheme.background
            ) {
                CardGrid(cards = MuscleGroup.getExercisesFromId(muscleGroupId).map { exercise ->
                    // Route to the section that display the gif of the exercise
                    exercise.setOnExerciseClick({ navController.navigate(Screen.Exercises.route + "/" + muscleGroupId + "/" + exercise.id) })
                })
            }
        }
    )
}

