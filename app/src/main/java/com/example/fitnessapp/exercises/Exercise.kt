package com.example.fitnessapp.exercises

import androidx.annotation.DrawableRes
import com.example.fitnessapp.CardItem

data class Exercise(
    override val id: String = "",
    override val name: String,
    @DrawableRes override val imageId: Int,
    val onExerciseClick: (exercise: CardItem) -> Unit
) : CardItem(
    id,
    name,
    imageId,
    onExerciseClick
)