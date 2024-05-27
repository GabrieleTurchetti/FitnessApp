package com.example.fitnessapp.room

import android.content.Context
import androidx.room.Room

class FitnessDatabase {
    // Maintain a reference of the database for all the other components
    companion object {
        lateinit var db: AppDatabase

        fun init(context: Context) {
            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "fitness"
            ).build()
        }
    }
}