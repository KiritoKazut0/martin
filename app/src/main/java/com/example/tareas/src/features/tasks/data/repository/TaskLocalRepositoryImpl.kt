package com.example.tareas.src.features.tasks.data.repository

import com.example.tareas.src.core.network.local.dao.TaskDao
import com.example.tareas.src.features.tasks.data.mappers.toDatabase
import com.example.tareas.src.features.tasks.data.mappers.toDomain
import com.example.tareas.src.features.tasks.domain.models.CreateTask
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.models.UpdateTask
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository


class TaskLocalRepositoryImpl (
    private val taskDao: TaskDao
): TaskRepository {


    override suspend fun create(request: CreateTask): Result<Task> {
        return try {
            val taskEntity = request.toDatabase(request.user_id)
            taskDao.insert(taskEntity)
            Result.success(taskEntity.toDomain())
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun delete(id: String): Result<String> {
        return try {
            taskDao.deleteById(id)
            Result.success("Deleted Successfully")
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun list(idUser: String): Result<List<Task>> {
        return try {
            val tasks = taskDao.listTask(idUser)
            Result.success(tasks.map { it.toDomain() })
        } catch (e: Exception){
            Result.failure(e)
        }
    }


    override suspend fun update(id: String, request: UpdateTask): Result<Task> {
        return try {
            val existing = taskDao.getTaskById(id)
                ?: return Result.failure(Exception("Task not found"))

            val updateEntity = existing.copy(
                title = request.title,
                description = request.description
            )

            taskDao.update(updateEntity)

            Result.success(updateEntity.toDomain())

        } catch (e: Exception){
            Result.failure(e)
        }
    }
}