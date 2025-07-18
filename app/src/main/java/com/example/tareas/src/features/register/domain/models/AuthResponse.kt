package com.example.tareas.src.features.register.domain.models

data class AuthResponse(
    val token: String,
    val user: User
)