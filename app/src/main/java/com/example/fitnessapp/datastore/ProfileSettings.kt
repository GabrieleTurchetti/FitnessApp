package com.example.fitnessapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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
        val HEIGHT = intPreferencesKey("height")
        val WEIGHT = intPreferencesKey("weight")
        val STEP_GOAL = intPreferencesKey("step_goal")
    }

    val getUsername: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME] ?: "Mario Rossi"
    }

    val getBirthDate: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[BIRTH_DATE] ?: "01012000"
    }

    val getGender: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[GENDER] ?: "Maschio"
    }

    val getHeight: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[HEIGHT] ?: 175
    }

    val getWeight: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[WEIGHT] ?: 70
    }

    val getStepGoal: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[STEP_GOAL] ?: 1000
    }

    val getHeightAndWeight: Flow<Pair<Int, Int>> = context.dataStore.data.map { preferences ->
        Pair(preferences[HEIGHT] ?: 175, preferences[WEIGHT] ?: 70)
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun saveBirthDate(birthDate: String) {
        context.dataStore.edit { preferences ->
            preferences[BIRTH_DATE] = birthDate
        }
    }

    suspend fun saveGender(gender: String) {
        context.dataStore.edit { preferences ->
            preferences[GENDER] = gender
        }
    }

    suspend fun saveHeight(height: Int) {
        context.dataStore.edit { preferences ->
            preferences[HEIGHT] = height
        }
    }

    suspend fun saveWeight(weight: Int) {
        context.dataStore.edit { preferences ->
            preferences[WEIGHT] = weight
        }
    }

    suspend fun saveStepGoal(stepGoal: Int) {
        context.dataStore.edit { preferences ->
            preferences[STEP_GOAL] = stepGoal
        }
    }
}