package com.example.tareas.src.features.tasks.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareas.src.core.userstorage.data.UserStorageRepositoryImpl
import com.example.tareas.src.core.userstorage.domain.UserStorageRepository
import com.example.tareas.src.features.tasks.domain.models.Task
import com.example.tareas.src.features.tasks.domain.usecase.ListTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListTaskViewModel (
    private val listTaskUseCase : ListTaskUseCase,
    private val userStorage: UserStorageRepository
): ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchTasksForUser()
    }


    fun fetchTasksForUser() {
        viewModelScope.launch {
            try {
                val userId = userStorage.getUserId().toString()
                _isLoading.value = true
                val tasksResult = listTaskUseCase(userId).getOrThrow()
                println("Tasks loaded for user : $tasksResult")
                _tasks.value = tasksResult
                _errorMessage.value = null
            }  catch (e: Exception) {
                _errorMessage.value = "Error inesperado. Hubo un problema al consumir el servicio"
                println("Error loading tasks: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }



}