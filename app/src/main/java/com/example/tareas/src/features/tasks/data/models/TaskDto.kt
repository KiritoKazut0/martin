package com.example.tareas.src.features.tasks.data.models

import com.example.tareas.src.features.tasks.domain.models.Task

data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val date: String
){
    fun toDomain() = Task(
        id,
        title,
        description,
        date
    )
}