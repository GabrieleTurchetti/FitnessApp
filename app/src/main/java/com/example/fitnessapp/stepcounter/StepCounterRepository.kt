package com.example.fitnessapp.stepcounter

import android.util.Log
import com.example.fitnessapp.room.StepCount
import com.example.fitnessapp.room.StepsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class StepCounterRepository(
    private val stepsDao: StepsDao,
) {
    suspend fun storeSteps(steps: Int, todayStepsAtLastReboot: Int, initialSteps: Int) = withContext(Dispatchers.IO) {
        val stepCount = StepCount(
            date = LocalDate.now().toString(),
            steps = steps,
            stepsAtLastReboot = todayStepsAtLastReboot,
            initialSteps = initialSteps
        )
        Log.d("STEP_COUNT_LISTENER", "Storing steps: $stepCount")
        stepsDao.insert(stepCount)
    }

    suspend fun getStepsByDate(date: String = LocalDate.now().toString()): Int = withContext(Dispatchers.IO) {
        val dateStepCounter = stepsDao.getByDate(date = date)

        if (dateStepCounter.isEmpty()) {
            -1
        }
        else {
            dateStepCounter.first().steps
        }
    }

    suspend fun getTodayStepsAtLastReboot(): Int = withContext(Dispatchers.IO) {
        val today = LocalDate.now().toString()
        val todayStepCounter = stepsDao.getByDate(date = today)

        if (todayStepCounter.isEmpty()) {
            -1
        }
        else {
            Log.d("STEP_COUNT_LISTENER", "Today Steps: $todayStepCounter")
            todayStepCounter.first().stepsAtLastReboot
        }
    }

    suspend fun getInitialStepsByDate(date: String): Int = withContext(Dispatchers.IO) {
        val dateStepCounter = stepsDao.getByDate(date = date)

        if (dateStepCounter.isEmpty()) {
            -1
        }
        else {
            Log.d("STEP_COUNT_LISTENER", "Today Steps: $dateStepCounter")
            dateStepCounter.first().initialSteps
        }
    }
}