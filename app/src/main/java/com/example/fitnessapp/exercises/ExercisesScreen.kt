package com.example.fitnessapp.exercises

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.fitnessapp.CardGrid
import com.example.fitnessapp.R

@Composable
fun ExercisesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ExercisesTopAppBar()
        },
        content = { padding ->
            Surface(
                modifier = Modifier.padding(padding),
                color = MaterialTheme.colorScheme.background
            ) {
                CardGrid(cards = listOf(
                    MuscleGroup.Chest.toCardCell(),
                    MuscleGroup.Shoulders.toCardCell(),
                    MuscleGroup.Biceps.toCardCell(),
                    MuscleGroup.Triceps.toCardCell(),
                    MuscleGroup.Abs.toCardCell(),
                    MuscleGroup.Back.toCardCell(),
                    MuscleGroup.Legs.toCardCell(),
                    MuscleGroup.Calves.toCardCell()
                ))
            }
        }
    )
}