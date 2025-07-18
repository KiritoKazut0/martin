package com.example.tareas.src.features.tasks.presentation.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tareas.src.core.userstorage.domain.UserStorageRepository
import com.example.tareas.src.features.tasks.domain.usecase.ListTaskUseCase
import com.example.tareas.src.features.tasks.presentation.viewModel.ListTaskViewModel

class ListTaskViewModelFactory (
    private val listTaskUseCase: ListTaskUseCase,
    private val userStorage: UserStorageRepository
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ListTaskViewModel::class.java)){
            return ListTaskViewModel(listTaskUseCase, userStorage) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}