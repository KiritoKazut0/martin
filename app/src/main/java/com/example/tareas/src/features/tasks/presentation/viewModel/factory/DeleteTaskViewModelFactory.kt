package com.example.tareas.src.features.tasks.presentation.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tareas.src.features.tasks.domain.usecase.DeleteTaskUseCase
import com.example.tareas.src.features.tasks.presentation.viewModel.DeleteTaskViewModel

class DeleteTaskViewModelFactory(
    private val deleteTaskUseCase: DeleteTaskUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeleteTaskViewModel::class.java)){
            return DeleteTaskViewModel(deleteTaskUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}