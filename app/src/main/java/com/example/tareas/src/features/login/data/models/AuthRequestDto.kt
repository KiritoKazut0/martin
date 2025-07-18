package com.example.tareas.src.features.login.data.models

import com.example.tareas.src.features.login.domain.models.AuthRequest

data class AuthRequestDto(
    val email: String,
    val password: String
) {
    fun toDomain() = AuthRequest(email, password)
}