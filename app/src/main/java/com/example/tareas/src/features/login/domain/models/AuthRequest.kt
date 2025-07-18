package com.example.tareas.src.features.login.domain.models
import com.example.tareas.src.features.login.data.models.AuthRequestDto

data class AuthRequest(
    val email: String,
    val password: String
) {
    fun toDo() = AuthRequestDto(email, password)
}