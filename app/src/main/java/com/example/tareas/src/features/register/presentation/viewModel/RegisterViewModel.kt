package com.example.tareas.src.features.register.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareas.src.features.register.domain.models.AuthRequest
import com.example.tareas.src.features.register.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val authUseCase: AuthUseCase): ViewModel(){

    private var _username = MutableStateFlow<String>("")
    val username : StateFlow<String> = _username

    private var _email = MutableStateFlow<String>("")
    val email : StateFlow<String> = _email

    private var _password = MutableStateFlow<String>("")
    val password : StateFlow<String> = _password

    private val _message = MutableStateFlow<String>("")
    val message: StateFlow<String> = _message

    private val _success =MutableStateFlow<Boolean>(false)
    val success: StateFlow<Boolean> = _success

    fun onChangeUsername(username: String){
        _username.value = username
    }

    fun onChangeEmail(email: String){
        _email.value = email
    }

    fun onChangePassword (password: String) {
        _password.value = password
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private  fun isPasswordValid(password: String): Boolean {
        return password.length >= 5
    }

    private fun isUsernameValid(username: String): Boolean {
        return username.length >= 5
    }

    fun validateFields(): Boolean {
        val currentUsername = _username.value
        val currentEmail = _email.value
        val currentPassword = _password.value

        return when {
            currentUsername.isBlank() -> {
                _message.value = "El nombre de usuario no puede estar vacío"
                _success.value = false
                false
            }

            currentEmail.isBlank() -> {
                _message.value = "El correo electrónico no puede estar vacío"
                _success.value = false
                false
            }

            currentPassword.isBlank() -> {
                _message.value = "La contraseña no puede estar vacía"
                _success.value = false
                false
            }

            !isUsernameValid(currentUsername) -> {
                _message.value = "El nombre de usuario debe tener al menos 4 caracteres"
                _success.value = false
                false
            }

            !isEmailValid(currentEmail) -> {
                _message.value = "Por favor, introduce una dirección de correo válida"
                _success.value = false
                false
            }

            !isPasswordValid(currentPassword) -> {
                _message.value = "La contraseña debe tener al menos 4 caracteres"
                _success.value = false
                false
            }

            else -> {
                _message.value = ""
                true
            }
        }
    }

    fun onClick() {
        if (!validateFields()) return

        val newUser = AuthRequest(
            username = _username.value,
            email = _email.value,
            password = _password.value
        )

        viewModelScope.launch {
            try {
                val result = authUseCase.invoke(newUser).getOrThrow()
                Log.d("RegisterViewModel", "Registro exitoso con ID: ${result.user.id}")
                _message.value = "Registro exitoso"
                _success.value = true

            } catch (e: Exception) {

                Log.e("RegisterViewModel", "Error en el registro: ${e.message}", e)
                _message.value = e.message ?: "Unknown error"
                _success.value = false
            }
        }
    }

}