package com.example.fitnessapp.stepcounter.steps

import com.example.fitnessapp.room.FitnessDatabase
import com.example.fitnessapp.room.Steps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

// Acts as an intermediary for the steps table operations
class StepsRepository {
    companion object {
        val fitnessDao = FitnessDatabase.db.fitnessDao()

        suspend fun insertSteps(steps: Int, todayStepsAtLastReboot: Int, initialSteps: Int) =
            withContext(Dispatchers.IO) {
                val newSteps = Steps(
                    date = LocalDate.now().toString(),
                    steps = steps,
                    stepsAtLastReboot = todayStepsAtLastReboot,
                    initialSteps = initialSteps
                )
                fitnessDao.insertSteps(newSteps)
            }

        suspend fun getStepsByDate(date: String = LocalDate.now().toString()): Int =
            withContext(Dispatchers.IO) {
                val steps = fitnessDao.getStepsByDate(date = date)

                if (steps.isEmpty()) {
                    -1
                } else {
                    steps.first().steps
                }
            }

        suspend fun getTodayStepsAtLastReboot(): Int = withContext(Dispatchers.IO) {
            val today = LocalDate.now().toString()
            val todaySteps = fitnessDao.getStepsByDate(date = today)

            if (todaySteps.isEmpty()) {
                -1
            } else {
                todaySteps.first().stepsAtLastReboot
            }
        }

        suspend fun getInitialStepsByDate(date: String): Int = withContext(Dispatchers.IO) {
            val dateStepCounter = fitnessDao.getStepsByDate(date = date)

            if (dateStepCounter.isEmpty()) {
                -1
            } else {
                dateStepCounter.first().initialSteps
            }
        }
    }
}