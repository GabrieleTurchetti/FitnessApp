package com.example.fitnessapp.stepcounter

import android.util.Log
import com.example.fitnessapp.room.FitnessDao
import com.example.fitnessapp.room.Steps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class StepCounterRepository(
    private val fitnessDao: FitnessDao,
) {
    suspend fun storeSteps(steps: Int, todayStepsAtLastReboot: Int, initialSteps: Int) = withContext(Dispatchers.IO) {
        val stepCounter = Steps(
            date = LocalDate.now().toString(),
            steps = steps,
            stepsAtLastReboot = todayStepsAtLastReboot,
            initialSteps = initialSteps
        )
        Log.d("STEP_COUNT_LISTENER", "Storing steps: $stepCounter")
        fitnessDao.insertSteps(stepCounter)
    }

    suspend fun getStepsByDate(date: String = LocalDate.now().toString()): Int = withContext(Dispatchers.IO) {
        val dateSteps = fitnessDao.getStepsByDate(date = date)

        if (dateSteps.isEmpty()) {
            -1
        }
        else {
            dateSteps.first().steps
        }
    }

    suspend fun getTodayStepsAtLastReboot(): Int = withContext(Dispatchers.IO) {
        val today = LocalDate.now().toString()
        val todaySteps = fitnessDao.getStepsByDate(date = today)

        if (todaySteps.isEmpty()) {
            -1
        }
        else {
            Log.d("STEP_COUNT_LISTENER", "Today Steps: $todaySteps")
            todaySteps.first().stepsAtLastReboot
        }
    }

    suspend fun getInitialStepsByDate(date: String): Int = withContext(Dispatchers.IO) {
        val dateStepCounter = fitnessDao.getStepsByDate(date = date)

        if (dateStepCounter.isEmpty()) {
            -1
        }
        else {
            Log.d("STEP_COUNT_LISTENER", "Today Steps: $dateStepCounter")
            dateStepCounter.first().initialSteps
        }
    }
}