package com.example.fitnessapp.components

class BoxItem(
    val title: String,
    val content: String? = null,
    val pencil: Boolean? = false,
    val onBoxItemClick: (String) -> Unit = {}
) {
    enum class Content(val rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
    }
}