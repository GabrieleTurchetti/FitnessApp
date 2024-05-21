package com.example.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.room.Room
import com.example.fitnessapp.bottomnavigationbar.BottomNavigationBar
import com.example.fitnessapp.location.LocationService
import com.example.fitnessapp.room.AppDatabase
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "step-counter"
        ).build()

        Log.d("BD", "$db")

        setContent {
            FitnessAppTheme {
                BottomNavigationBar()
            }
        }
    }
}