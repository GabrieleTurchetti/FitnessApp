package com.example.fitnessapp.caloriesburned

import android.util.Log
import com.example.fitnessapp.room.Calories
import com.example.fitnessapp.room.FitnessDao
import com.example.fitnessapp.room.Steps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CaloriesBurnedRepository(
    private val fitnessDao: FitnessDao,
) {
    suspend fun insertCalories(calories: Int) = withContext(Dispatchers.IO) {
        val caloriesBurned = Calories(
            date = LocalDate.now().toString(),
            calories = calories
        )
        Log.d("STEP_COUNT_LISTENER", "Storing steps: $caloriesBurned")
        fitnessDao.insertCalories(caloriesBurned)
    }

    suspend fun getCaloriesByDate(date: String = LocalDate.now().toString()): Int = withContext(Dispatchers.IO) {
        val dateCalories = fitnessDao.getCaloriesByDate(date = date)

        if (dateCalories.isEmpty()) {
            -1
        }
        else {
            dateCalories.first().calories
        }
    }
}