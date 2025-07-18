package com.example.tareas.src.features.login.data.models

import com.example.tareas.src.features.login.domain.models.User

data class UserDto(
    val id: String,
    val email: String,
    val username: String
){
    fun toDomain() = User(id, username, email)
}