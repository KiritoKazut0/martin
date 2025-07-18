package com.example.tareas.src.features.tasks.domain.usecase

import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.models.UpdateTask
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

class UpdateTaskUseCase (private val repository: TaskRepository){
    suspend operator fun invoke(id: String ,request: UpdateTask): Result<Task>{
        return  repository.update(id, request)
    }
}