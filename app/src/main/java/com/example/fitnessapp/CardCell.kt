package com.example.fitnessapp

import androidx.annotation.DrawableRes

data class CardCell(
    val name: String,
    @DrawableRes val imageId: Int,
    val onCardCellClick: () -> Unit
)