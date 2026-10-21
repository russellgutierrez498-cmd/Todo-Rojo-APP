package com.example.todo_list.features.tracker.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StatsScreen(viewModel: HabitViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val totalActividadesPosibles = state.activities.size
    var totalCompletadas = 0
    val idsActivos = state.activities.map { it.id }
    state.dailyCompletions.values.forEach { completedList ->
        totalCompletadas += completedList.count { it in idsActivos }
    }

    val porcentaje: Float = if (totalActividadesPosibles > 0) {
        totalCompletadas.toFloat() / totalActividadesPosibles.toFloat()
    } else {
        0f
    }

    val porcentajeInt = (porcentaje * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tus Estadísticas", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(32.dp))

        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(200.dp),
                color = Color.LightGray.copy(alpha = 0.3f),
                strokeWidth = 20.dp,
                strokeCap = StrokeCap.Round
            )
            CircularProgressIndicator(
                progress = { porcentaje },
                modifier = Modifier.size(200.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 20.dp,
                strokeCap = StrokeCap.Round
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$porcentajeInt%",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Completado", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Resumen del Mes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Tareas totales marcadas: $totalCompletadas", fontSize = 16.sp)
                Text("Total de hábitos seguidos: ${state.activities.size}", fontSize = 16.sp)
            }
        }
    }
}