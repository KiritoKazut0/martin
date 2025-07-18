package com.example.tareas.src.features.tasks.presentation.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tareas.src.features.tasks.domain.usecase.UpdateTaskUseCase
import com.example.tareas.src.features.tasks.presentation.viewModel.UpdateTaskViewModel

class UpdateTaskVIewModelFactory (
    private val updateTaskUseCase: UpdateTaskUseCase
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateTaskViewModel::class.java)){
            return UpdateTaskViewModel(updateTaskUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}