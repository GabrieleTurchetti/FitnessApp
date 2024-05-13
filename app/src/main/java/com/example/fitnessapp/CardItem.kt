package com.example.fitnessapp

import androidx.annotation.DrawableRes

open class CardItem(
    open val name: String,
    open val id: String,
    open @DrawableRes val imageId: Int,
    open val onCardItemClick: (CardItem?) -> Unit
)