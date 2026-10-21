package com.example.todo_list.features.tracker.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MatrixScreen(viewModel: HabitViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val days = (1..30).toList()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Progreso del Mes", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.activities.isEmpty()) {
            Text("Agrega actividades en la otra pestaña para ver la matriz.")
            return@Column
        }

        Column(modifier = Modifier.horizontalScroll(scrollState)) {

            Row(modifier = Modifier.padding(start = 40.dp)) {
                state.activities.forEach { activity ->
                    Text(
                        text = activity.name,
                        modifier = Modifier.width(100.dp).padding(end = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            LazyColumn {
                items(days) { day ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "Día $day",
                            modifier = Modifier.width(40.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        state.activities.forEach { activity ->
                            val isCompleted = state.dailyCompletions[day]?.contains(activity.id) == true

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.width(100.dp)
                            ) {
                                Checkbox(
                                    checked = isCompleted,
                                    onCheckedChange = {
                                        viewModel.toggleActivityCompletion(day, activity.id)
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1E3A8A))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}