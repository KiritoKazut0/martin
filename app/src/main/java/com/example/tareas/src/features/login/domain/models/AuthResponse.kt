package com.example.tareas.src.features.login.domain.models

data class AuthResponse(
    val token: String,
    val user: User
)