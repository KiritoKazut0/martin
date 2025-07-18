package com.example.tareas.src.features.register.data.repository

import com.example.tareas.src.features.register.data.datasource.AuthService
import com.example.tareas.src.features.register.domain.models.AuthRequest
import com.example.tareas.src.features.register.domain.models.AuthResponse
import com.example.tareas.src.features.register.domain.repository.AuthRepository

class AuthRepositoryImpl(private val api: AuthService): AuthRepository {
    override suspend fun register(request: AuthRequest): Result<AuthResponse> {
        return try {
            val response = api.registerUser(request.toDo())
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}