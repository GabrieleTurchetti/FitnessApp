package com.example.fitnessapp.utils

fun camelCaseToSnakeCase(text: String): String {
    val pattern = "(?<=.)[A-Z]".toRegex()
    return text.replace(pattern, "_$0").lowercase()
}