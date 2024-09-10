package com.example.fitnessapp.exercises.musclegroup.exercise

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.fitnessapp.ui.common.CardItem
import com.example.fitnessapp.utils.camelCaseToSnakeCase

data class Exercise(
    override val id: String = "",
    override val name: String,
    @DrawableRes override val imageId: Int,
    var onExerciseClick: () -> Unit
) : CardItem(
    id,
    name,
    imageId,
    onExerciseClick
) {
    fun setOnExerciseClick(onExerciseClick: () -> Unit): Exercise {
        return this.copy(onExerciseClick = onExerciseClick)
    }

    companion object {
        // Get the resource id of the gif related to the exercise
        @Composable
        fun getGifResourceFromId(exerciseId: String): Int {
            return LocalContext.current.resources.getIdentifier(
                camelCaseToSnakeCase(exerciseId) + "_gif",
                "drawable",
                LocalContext.current.packageName)
        }
    }
}