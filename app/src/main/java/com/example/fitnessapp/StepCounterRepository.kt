package com.example.fitnessapp

import android.util.Log
import com.example.fitnessapp.room.StepCount
import com.example.fitnessapp.room.StepsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.LocalTime

class StepCounterRepository(
    private val stepsDao: StepsDao,
) {
    suspend fun storeSteps(steps: Int, stepsAtLastReboot: Int) = withContext(Dispatchers.IO) {
        val stepCount = StepCount(
            date = LocalDate.now().toString(),
            steps = steps,
            stepsAtLastReboot = stepsAtLastReboot
        )
        Log.d("STEP_COUNT_LISTENER", "Storing steps: $stepCount")
        stepsDao.insert(stepCount)
    }

    suspend fun getStepsByDate(date: String = LocalDate.now().toString()): Int = withContext(Dispatchers.IO) {
        val dateStepCounter = stepsDao.getByDate(date = date)

        if (dateStepCounter.isEmpty()) {
            0
        }
        else {
            Log.d("STEP_COUNT_LISTENER", "Today Steps: $dateStepCounter")
            dateStepCounter.first().steps
        }
    }

    suspend fun getStepsAtLastReboot(): Int = withContext(Dispatchers.IO) {
        val today = LocalDate.now().toString()
        val todayStepCounter = stepsDao.getByDate(date = today)

        if (todayStepCounter.isEmpty()) {
            0
        }
        else {
            Log.d("STEP_COUNT_LISTENER", "Today Steps: $todayStepCounter")
            todayStepCounter.first().stepsAtLastReboot
        }
    }
}