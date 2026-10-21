package com.example.todo_list

import java.util.UUID

data class HabitActivity (
    val id: String = UUID.randomUUID().toString(),
    val name: String
)


data class MonthProgress (
    val monthName : String = "Octubre 2026",
    val activities: List<HabitActivity> = emptyList(),
    val dailyCompletions: Map<Int, List<String>> = emptyMap()
)