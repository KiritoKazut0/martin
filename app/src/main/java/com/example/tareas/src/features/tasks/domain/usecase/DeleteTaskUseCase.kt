package com.example.tareas.src.features.tasks.domain.usecase

import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(id: String): Result<String>{
        return repository.delete(id)
    }
}