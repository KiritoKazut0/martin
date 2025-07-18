package com.example.tareas.src.features.tasks.domain.usecase

import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.core.userstorage.domain.UserStorageRepository
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

data class ListTaskUseCase(
    private val repository: TaskRepository
){
    suspend operator fun invoke(userId: String): Result<List<Task>> {
        return repository.list(userId)
    }
}