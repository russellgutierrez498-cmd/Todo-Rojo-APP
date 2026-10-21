package com.example.todo_list.features.tracker.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.example.todo_list.features.tracker.domain.model.HabitActivity
import com.example.todo_list.features.tracker.domain.model.MonthProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "habit_prefs")

class TrackerRepository(private val context: Context) {

    private val PROGRESS_KEY = stringPreferencesKey("month_progress")
    private val gson = Gson()

    val progressFlow: Flow<MonthProgress> = context.dataStore.data.map { preferences ->
        val jsonString = preferences[PROGRESS_KEY]
        if (jsonString != null) {
            gson.fromJson(jsonString, MonthProgress::class.java)
        } else {
            MonthProgress(
                activities = listOf(
                    HabitActivity(name = "Leer 20 mins"),
                    HabitActivity(name = "Hacer ejercicio")
                )
            )
        }
    }

    suspend fun saveProgress(progress: MonthProgress) {
        context.dataStore.edit { preferences ->
            preferences[PROGRESS_KEY] = gson.toJson(progress)
        }
    }
}