package com.example.todo_list.features.tracker.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainTrackerScreen(viewModel: HabitViewModel) {
    var tabSeleccionado by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = tabSeleccionado == 0,
                    onClick = { tabSeleccionado = 0 },
                    icon = { Icon(Icons.Default.List, contentDescription = "Lista") },
                    label = { Text("Actividades") }
                )
                NavigationBarItem(
                    selected = tabSeleccionado == 1,
                    onClick = { tabSeleccionado = 1 },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Matriz") },
                    label = { Text("Progreso") }
                )
                NavigationBarItem(
                    selected = tabSeleccionado == 2,
                    onClick = { tabSeleccionado = 2 },
                    icon = { Icon(Icons.Default.Info, contentDescription = "Stats") },
                    label = { Text("Estadísticas") }
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            when (tabSeleccionado) {
                0 -> ActivitiesScreen(viewModel)
                1 -> MatrixScreen(viewModel)
                2 -> StatsScreen(viewModel)
            }
        }
    }
}