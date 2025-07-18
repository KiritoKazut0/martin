package com.example.tareas.src.features.register.data.models

import com.example.tareas.src.features.register.domain.models.AuthRequest

data class AuthRequestDto(
    val username: String,
    val email: String,
    val password: String
) {
    fun toDomain() = AuthRequest(username, email, password)
}