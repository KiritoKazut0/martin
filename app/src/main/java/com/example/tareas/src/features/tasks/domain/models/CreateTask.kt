package com.example.tareas.src.features.tasks.domain.models

import com.example.tareas.src.features.tasks.data.models.CreateTaskDto

data class CreateTask(
    val user_id: String,
    val title: String,
    val description: String
) {
    fun toDo() = CreateTaskDto(
        user_id,
        title,
        description
    )
}