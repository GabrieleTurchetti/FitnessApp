package com.example.fitnessapp.stepcounter

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.MainActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
class StepCounterViewModel: ViewModel() {
    var stepCounter: StepCounter? = null

    fun init(context: Context) {
        stepCounter = StepCounter(context)
        stepCounter?.steps()
    }

    fun registerListenerStepCounter(context: Context) {
        stepCounter?.registerListener()
    }

    fun unregisterListenerStepCounter(context: Context) {
        StepCounter(context).steps()
    }

    suspend fun getSteps(date: String): Int {
        return StepCounterRepository(MainActivity.db.stepsDao()).getStepsByDate(date)
    }
}