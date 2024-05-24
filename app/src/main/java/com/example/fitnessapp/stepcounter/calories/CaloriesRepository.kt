package com.example.fitnessapp.stepcounter.calories

import com.example.fitnessapp.room.Calories
import com.example.fitnessapp.room.FitnessDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CaloriesRepository(
    private val fitnessDao: FitnessDao,
) {
    suspend fun insertCalories(calories: Int) = withContext(Dispatchers.IO) {
        val calories = Calories(
            date = LocalDate.now().toString(),
            calories = calories
        )
        fitnessDao.insertCalories(calories)
    }
    suspend fun getCaloriesByDate(date: String = LocalDate.now().toString()): Int = withContext(
        Dispatchers.IO) {
        val calories = fitnessDao.getCaloriesByDate(date = date)

        if (calories.isEmpty()) {
            -1
        }
        else {
            calories.first().calories
        }
    }
}