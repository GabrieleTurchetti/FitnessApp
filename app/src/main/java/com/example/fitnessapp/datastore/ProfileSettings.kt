package com.example.fitnessapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileSettings(
    private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("ProfileSettings")
        val USERNAME = stringPreferencesKey("username")
        val BIRTH_DATE = stringPreferencesKey("birth_date")
        val GENDER = stringPreferencesKey("gender")
        val HEIGHT = stringPreferencesKey("height")
        val WEIGHT = stringPreferencesKey("weight")
        val STEP_GOAL = stringPreferencesKey("step_goal")
    }

    val getUsername: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USERNAME] ?: ""
    }

    val getBirthDate: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[BIRTH_DATE] ?: ""
    }

    val getGender: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[GENDER] ?: ""
    }

    val getHeight: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[HEIGHT] ?: ""
    }

    val getWeight: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[WEIGHT] ?: ""
    }

    val getStepGoal: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[STEP_GOAL] ?: ""
    }

    suspend fun setUsername(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = name
        }
    }

    suspend fun setBirthDate(name: String) {
        context.dataStore.edit { preferences ->
            preferences[BIRTH_DATE] = name
        }
    }

    suspend fun setGender(name: String) {
        context.dataStore.edit { preferences ->
            preferences[GENDER] = name
        }
    }

    suspend fun setHeight(name: String) {
        context.dataStore.edit { preferences ->
            preferences[HEIGHT] = name
        }
    }

    suspend fun setWeight(name: String) {
        context.dataStore.edit { preferences ->
            preferences[WEIGHT] = name
        }
    }

    suspend fun setStepGoal(name: String) {
        context.dataStore.edit { preferences ->
            preferences[STEP_GOAL] = name
        }
    }
}