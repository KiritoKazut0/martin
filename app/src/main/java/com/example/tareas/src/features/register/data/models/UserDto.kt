package com.example.tareas.src.features.register.data.models

import com.example.tareas.src.features.register.domain.models.User

data class UserDto(
    val id: String,
    val email: String,
    val username: String
) {
    fun toDomain() = User(id, username, email)
}