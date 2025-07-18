package com.example.tareas.src.features.tasks.data.repository

import com.example.tareas.src.features.tasks.data.datasource.remote.TaskService
import com.example.tareas.src.features.tasks.domain.models.CreateTask
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.models.UpdateTask
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

class TaskRepositoryImpl(private val api: TaskService): TaskRepository{
    override suspend fun create(request: CreateTask): Result<Task> {
        return  try {
            val response = api.createTask(request.toDo())
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: String): Result<String> {
        return try {
            val response = api.deleteTask(id)
            Result.success(response.msg)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun list(idUser: String): Result<List<Task>> {
        return try {
            val response = api.listTask(idUser)
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(id: String, request: UpdateTask): Result<Task> {
        return  try {
            val response = api.updateTask(id, request.toDo())
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}