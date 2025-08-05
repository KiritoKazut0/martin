package com.example.tareas.src.features.tasks.data.mappers


import com.example.tareas.src.core.network.local.entities.TaskEntity
import com.example.tareas.src.features.tasks.domain.models.CreateTask
import com.example.tareas.src.features.tasks.domain.models.Task
import java.util.Date
import java.util.UUID

fun TaskEntity.toDomain(): Task = Task(
    id, title, description, date
)



fun CreateTask.toDatabase(userId: String): TaskEntity = TaskEntity(
    id = UUID.randomUUID().toString(),
    id_user = userId,
    title = this.title,
    description = this.description,
    date = Date.from(java.time.Instant.now()).toString()

)