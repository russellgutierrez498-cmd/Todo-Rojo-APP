package com.example.todo_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo_list.features.tracker.data.repository.TrackerRepository
import com.example.todo_list.features.tracker.presentation.HabitViewModel
import com.example.todo_list.ui.theme.TodolistTheme
import com.example.todo_list.features.tracker.presentation.MatrixScreen

class MainActivity : ComponentActivity() {

    private val repository by lazy { TrackerRepository(applicationContext) }

    private val viewModel: HabitViewModel by viewModels {
        viewModelFactory {
            initializer { HabitViewModel(repository) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodolistTheme {
                var pestañaSeleccionada by remember { mutableIntStateOf(0) }

                Scaffold(
                    bottomBar = {
                        NavigationBar(containerColor = Color.White) {
                            NavigationBarItem(
                                selected = pestañaSeleccionada == 0,
                                onClick = { pestañaSeleccionada = 0 },
                                icon = { Icon(Icons.Default.List, contentDescription = "Lista") },
                                label = { Text("Actividades") }
                            )
                            NavigationBarItem(
                                selected = pestañaSeleccionada == 1,
                                onClick = { pestañaSeleccionada = 1 },
                                icon = { Icon(Icons.Default.DateRange, contentDescription = "Matriz") },
                                label = { Text("Progreso") }
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
                        if (pestañaSeleccionada == 0) {
                            ActivitiesScreen(viewModel)
                        } else {
                            MatrixScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivitiesScreen(viewModel: HabitViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var nuevaActividad by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A))
        ) {
            Text(
                text = state.monthName,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text("Actividades por hacer", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = nuevaActividad,
                onValueChange = { nuevaActividad = it },
                label = { Text("Nueva actividad") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.addActivity(nuevaActividad)
                    nuevaActividad = ""
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Agregar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.activities) { actividad ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = actividad.name,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}