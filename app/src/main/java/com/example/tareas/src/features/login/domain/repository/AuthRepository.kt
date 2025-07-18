package com.example.tareas.src.features.login.domain.repository

import com.example.tareas.src.features.login.domain.models.AuthRequest
import com.example.tareas.src.features.login.domain.models.AuthResponse

interface AuthRepository {
    suspend fun login(request: AuthRequest): Result<AuthResponse>
}