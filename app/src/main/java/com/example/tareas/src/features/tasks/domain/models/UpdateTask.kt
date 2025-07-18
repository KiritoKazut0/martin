package com.example.tareas.src.features.tasks.domain.models

import com.example.tareas.src.features.tasks.data.models.UpdateTaskDto

data class UpdateTask(
    val title: String,
    val description: String
) {
    fun toDo() = UpdateTaskDto(
       title,
        description
    )
}