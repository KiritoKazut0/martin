package com.example.tareas.src.features.register.domain.repository

import com.example.tareas.src.features.register.domain.models.AuthRequest
import com.example.tareas.src.features.register.domain.models.AuthResponse

interface AuthRepository {
    suspend fun register(request: AuthRequest): Result<AuthResponse>
}