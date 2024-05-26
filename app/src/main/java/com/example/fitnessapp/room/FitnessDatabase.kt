package com.example.fitnessapp.room

import android.content.Context
import androidx.room.Room

class FitnessDatabase {
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