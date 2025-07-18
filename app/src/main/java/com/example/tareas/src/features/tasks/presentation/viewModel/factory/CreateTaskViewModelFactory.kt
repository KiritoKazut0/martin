package com.example.tareas.src.features.tasks.presentation.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tareas.src.core.hardware.domain.NotificationRepository
import com.example.tareas.src.core.hardware.domain.VibratorRepository
import com.example.tareas.src.core.userstorage.domain.UserStorageRepository
import com.example.tareas.src.features.tasks.domain.usecase.CreateTaskUseCase
import com.example.tareas.src.features.tasks.presentation.viewModel.CreateTaskViewModel

class CreateTaskViewModelFactory (
    private val createTaskUseCase: CreateTaskUseCase,
    private val userStorage: UserStorageRepository,
    private val vibratorRepository: VibratorRepository,
    private val notificationRepository: NotificationRepository
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTaskViewModel::class.java)){
            return CreateTaskViewModel(createTaskUseCase, userStorage, vibratorRepository, notificationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}