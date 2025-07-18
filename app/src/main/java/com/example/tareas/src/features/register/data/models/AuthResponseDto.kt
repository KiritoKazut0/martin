package com.example.tareas.src.features.register.data.models

import com.example.tareas.src.features.register.domain.models.AuthResponse

data class AuthResponseDto(
    val token: String,
    val user: UserDto
) {
    fun toDomain() = AuthResponse(
        token, user = user.toDomain()
    )
}