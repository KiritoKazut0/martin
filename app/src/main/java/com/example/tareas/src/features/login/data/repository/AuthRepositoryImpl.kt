package com.example.tareas.src.features.login.data.repository

import com.example.tareas.src.features.login.data.datasource.AuthService
import com.example.tareas.src.features.login.domain.models.AuthRequest
import com.example.tareas.src.features.login.domain.models.AuthResponse
import com.example.tareas.src.features.login.domain.repository.AuthRepository

class AuthRepositoryImpl(private val api: AuthService): AuthRepository {
    override suspend fun login(request: AuthRequest): Result<AuthResponse> {
       return try {
           val response = api.loginUser(request.toDo())
           Result.success(response.toDomain())
       } catch (e: Exception) {
           Result.failure(e)
       }
    }
}