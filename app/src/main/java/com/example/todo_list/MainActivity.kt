package com.example.todo_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list.features.tracker.data.repository.TrackerRepository
import com.example.todo_list.features.tracker.presentation.HabitViewModel
import com.example.todo_list.features.tracker.presentation.MainTrackerScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = TrackerRepository(applicationContext)

        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitViewModel(repository) as T
            }
        }

        setContent {
            MaterialTheme {
                val viewModel: HabitViewModel = viewModel(factory = factory)

                MainTrackerScreen(viewModel)
            }
        }
    }
}