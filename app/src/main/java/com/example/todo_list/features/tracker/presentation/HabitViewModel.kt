package com.example.todo_list.features.tracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.features.tracker.data.repository.TrackerRepository
import com.example.todo_list.features.tracker.domain.model.HabitActivity
import com.example.todo_list.features.tracker.domain.model.MonthProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: TrackerRepository) : ViewModel() {

    val uiState: StateFlow<MonthProgress> = repository.progressFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MonthProgress()
        )

    fun addActivity(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            val current = uiState.value
            repository.saveProgress(
                current.copy(activities = current.activities + HabitActivity(name = name.trim()))
            )
        }
    }

    fun removeActivity(activityId: String) {
        viewModelScope.launch {
            val current = uiState.value
            repository.saveProgress(
                current.copy(activities = current.activities.filterNot { it.id == activityId })
            )
        }
    }

    fun toggleActivityCompletion(day: Int, activityId: String) {
        viewModelScope.launch {
            val current = uiState.value
            val dayList = current.dailyCompletions[day] ?: emptyList()
            val updatedList = if (activityId in dayList) dayList - activityId else dayList + activityId

            repository.saveProgress(
                current.copy(dailyCompletions = current.dailyCompletions + (day to updatedList))
            )
        }
    }
}