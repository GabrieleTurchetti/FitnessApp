package com.example.fitnessapp.stepcounter.calories

import com.example.fitnessapp.room.Calories
import com.example.fitnessapp.room.FitnessDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

// Acts as an intermediary for the calories table operations
class CaloriesRepository {
    companion object {
        val fitnessDao = FitnessDatabase.db.fitnessDao()

        suspend fun insertCalories(calories: Int) = withContext(Dispatchers.IO) {
            val newCalories = Calories(
                date = LocalDate.now().toString(),
                calories = calories
            )
            fitnessDao.insertCalories(newCalories)
        }

        suspend fun getCaloriesByDate(date: String = LocalDate.now().toString()): Int = withContext(
            Dispatchers.IO
        ) {
            val calories = fitnessDao.getCaloriesByDate(date = date)

            if (calories.isEmpty()) {
                0
            } else {
                calories.first().calories
            }
        }
    }
}