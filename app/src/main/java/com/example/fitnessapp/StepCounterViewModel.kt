package com.example.fitnessapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class StepCounterViewModel: ViewModel() {
    fun initStepCounter(context: Context) {
        viewModelScope.launch {
            StepCounter(context).steps()
        }
    }

    suspend fun getSteps(date: String): Int {
        return StepCounterRepository(MainActivity.db.stepsDao()).getStepsByDate(date)
    }
}