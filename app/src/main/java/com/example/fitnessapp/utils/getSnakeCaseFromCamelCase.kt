package com.example.fitnessapp.utils

fun String.getSnakeCaseFromCamelCase(): String {
    val pattern = "(?<=.)[A-Z]".toRegex()
    return this.replace(pattern, "_$0").lowercase()
}