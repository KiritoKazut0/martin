package com.example.tareas.src.features.tasks.domain.usecase

import com.example.tareas.src.features.tasks.domain.models.CreateTask
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

class CreateTaskUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(request: CreateTask): Result<Task>{
        return repository.create(request)
    }
}