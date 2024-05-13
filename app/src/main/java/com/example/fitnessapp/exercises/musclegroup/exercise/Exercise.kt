package com.example.fitnessapp.exercises.musclegroup.exercise

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.fitnessapp.CardItem
import com.example.fitnessapp.utils.getSnakeCaseFromCamelCase

data class Exercise(
    override val id: String = "",
    override val name: String,
    @DrawableRes override val imageId: Int,
    var onExerciseClick: (CardItem?) -> Unit
) : CardItem(
    id,
    name,
    imageId,
    onExerciseClick
) {
    fun setOnExerciseClick(onExerciseClick: (CardItem?) -> Unit): Exercise {
        return this.copy(onExerciseClick = onExerciseClick)
    }


    companion object {
        @Composable
        fun getGifFromId(exerciseId: String?): Int {
            Log.d("HERE", "${exerciseId}")
            val x = LocalContext.current.resources.getIdentifier(
                exerciseId?.getSnakeCaseFromCamelCase() + "_gif",
                "drawable",
                LocalContext.current.packageName)
            Log.d("TAG", "${x}")
            return x
        }
    }
}