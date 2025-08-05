package com.example.tareas.src.features.tasks.domain.usecase

import com.example.tareas.src.core.network.NetworkManager
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.models.UpdateTask
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

class UpdateTaskUseCase (
    private val repository: TaskRepository,
    private  val localRepository: TaskRepository,
    private  val networkManager: NetworkManager

){
    suspend operator fun invoke(id: String ,request: UpdateTask): Result<Task>{
        return if (networkManager.isConnected()) {
            repository.update(id, request)
        } else {
            localRepository.update(id, request)
        }
    }
}