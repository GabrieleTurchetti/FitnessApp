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
    suspend fun storeSteps(stepsSinceLastReboot: Long) = withContext(Dispatchers.IO) {
        val stepCount = StepCount(
            steps = stepsSinceLastReboot,
            createdAt = Instant.now().toString()
        )
        Log.d("STEP_COUNT_LISTENER", "Storing steps: $stepCount")
        stepsDao.insertAll(stepCount)
    }

    suspend fun loadTodaySteps(): Long = withContext(Dispatchers.IO) {
        val todayAtMidnight = (LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).toString())
        val todayDataPoints = stepsDao.loadAllStepsFromToday(startDateTime = todayAtMidnight)
        when {
            todayDataPoints.isEmpty() -> 0
            else -> {
                val firstDataPointOfTheDay = todayDataPoints.first()
                val latestDataPointSoFar = todayDataPoints.last()

                val todaySteps = latestDataPointSoFar.steps - firstDataPointOfTheDay.steps
                Log.d("STEP_COUNT_LISTENER", "Today Steps: $todaySteps")
                todaySteps
            }
        }
    }
}