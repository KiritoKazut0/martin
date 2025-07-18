package com.example.tareas.src.features.tasks.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareas.src.features.tasks.domain.models.UpdateTask
import com.example.tareas.src.features.tasks.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateTaskViewModel (
    private val updateTaskUseCase: UpdateTaskUseCase
): ViewModel(){
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description


    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message


    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun onTitleChanged(newTitle: String) {
        _title.value = newTitle
    }


    fun onDescriptionChanged(newDescription: String) {
        _description.value = newDescription
    }

    fun updateTask(idTask: String){
        val taskUpdate = UpdateTask(
            title = _title.value,
            description = _description.value
        )

        viewModelScope.launch {
            try {
                updateTaskUseCase(idTask, taskUpdate)
                _message.value = "Tarea Actualizada"
                _success.value = true
            } catch (e: Exception) {
                _message.value = e.message ?: "Unknown error"
                _success.value = false
            }
        }

    }
}