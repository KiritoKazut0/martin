package com.example.tareas.src.features.tasks.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareas.src.core.hardware.domain.NotificationRepository
import com.example.tareas.src.core.hardware.domain.VibratorRepository
import com.example.tareas.src.core.userstorage.domain.UserStorageRepository
import com.example.tareas.src.features.tasks.domain.models.CreateTask
import com.example.tareas.src.features.tasks.domain.usecase.CreateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateTaskViewModel(
    private val createTaskUseCase: CreateTaskUseCase,
    private val userStorage: UserStorageRepository,
    private val vibratorRepository: VibratorRepository,
    private val notificarionRepository: NotificationRepository
): ViewModel() {

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

    private fun clearFields() {
        _title.value = ""
        _description.value = ""
    }


    fun validateFields(): Boolean {
        val currentTitle = _title.value
        val currentDescription = _description.value

        return when {
            currentTitle.isBlank() -> {
                _message.value = "El titulo no puede estar vacio"
                _success.value = false
                false
            }
            currentDescription.isBlank() -> {
                _message.value = "La descripcion no puede estar vacio"
                _success.value = false
                false
            }
            else -> {
                _message.value = ""
                true
            }
        }
    }

    fun createTask() {
        if (!validateFields()) {
            _message.value = "Porfavor completa todos los campos"
            return
        }

        viewModelScope.launch { val idUser = userStorage.getUserId().toString()
            val task = CreateTask(
                user_id = idUser,
                title = _title.value,
                description = _description.value,
            )

            try {
               val result = createTaskUseCase(task).getOrThrow()
                _message.value = "Se creo correctamente"
                vibratorRepository.vibrate(500)
                notificarionRepository.activeNotification(
                    taskTitle = result.title,
                    idTask = result.id
                )
                _success.value = true
                clearFields()
            } catch (e: Exception) {
                _message.value = e.message ?: "Unknown error"
                _success.value = false
            }
        }
    }

}