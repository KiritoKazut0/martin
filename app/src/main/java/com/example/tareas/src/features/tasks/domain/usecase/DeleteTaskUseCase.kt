package com.example.tareas.src.features.tasks.domain.usecase

import com.example.tareas.src.core.network.NetworkManager
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository,
    private val localRepository: TaskRepository,
    private val networkManager: NetworkManager

){
    suspend operator fun invoke(id: String): Result<String>{
        return if (networkManager.isConnected()) {
            repository.delete(id)
        } else {
            localRepository.delete(id)
        }
    }
}