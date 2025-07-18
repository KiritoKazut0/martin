package com.example.tareas.src.features.register.domain.models

import com.example.tareas.src.features.register.data.models.AuthRequestDto

data class AuthRequest(
    val username: String,
    val email: String,
    val password: String
) {
    fun toDo() = AuthRequestDto(
        username,
        email,
        password
    )
}