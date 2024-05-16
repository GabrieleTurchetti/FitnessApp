package com.example.fitnessapp.common

import androidx.annotation.DrawableRes

open class CardItem(
    open val id: String,
    open val name: String,
    @DrawableRes open val imageId: Int,
    open val onCardItemClick: () -> Unit
)