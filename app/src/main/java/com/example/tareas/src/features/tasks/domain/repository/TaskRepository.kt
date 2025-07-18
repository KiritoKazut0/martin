package com.example.tareas.src.features.tasks.domain.repository

import com.example.tareas.src.features.tasks.domain.models.CreateTask
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.models.UpdateTask

interface TaskRepository {
    suspend fun list(idUser: String): Result<List<Task>>
    suspend fun create(request: CreateTask): Result<Task>
    suspend fun update(id: String ,request: UpdateTask): Result<Task>
    suspend fun delete(id: String): Result<String>
}