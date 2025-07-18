package com.example.tareas.src.features.tasks.domain.models

import com.example.tareas.src.features.tasks.data.models.TaskDto

data class Task(
    val id: String,
    val title: String,
    val description : String,
    val date: String
) {
    fun toDo() = TaskDto(
        id,
        title,
        description,
        date
    )
}