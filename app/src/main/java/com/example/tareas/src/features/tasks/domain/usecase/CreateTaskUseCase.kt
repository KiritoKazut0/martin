package com.example.tareas.src.features.tasks.domain.usecase

import com.example.tareas.src.core.network.NetworkManager
import com.example.tareas.src.features.tasks.domain.models.CreateTask
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

class CreateTaskUseCase(
    private val repository: TaskRepository,
    private val localRepository: TaskRepository,
    private val  networkManager: NetworkManager
    ){
    suspend operator fun invoke(request: CreateTask): Result<Task>{
        return if (networkManager.isConnected()){
            repository.create(request)
        } else{
            localRepository.create(request)
        }
    }
}