package com.example.tareas.src.features.tasks.data.models

import com.example.tareas.src.features.tasks.domain.models.CreateTask

data class CreateTaskDto(
    val  user_id: String,
    val title: String,
    val description: String,
){
    fun toDomain() = CreateTask(
        user_id,
        title,
        description,
    )
}