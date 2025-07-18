package com.example.tareas.src.features.register.domain.usecase

import com.example.tareas.src.features.register.domain.models.AuthRequest
import com.example.tareas.src.features.register.domain.models.AuthResponse
import com.example.tareas.src.features.register.domain.repository.AuthRepository
import com.example.tareas.src.features.register.domain.repository.SessionRepository

class AuthUseCase (
    private val repository: AuthRepository,
    private val sessionRepository: SessionRepository
){

    suspend fun invoke(request: AuthRequest): Result<AuthResponse>{
        val result = repository.register(request)

        result.onSuccess{ data ->
            sessionRepository.saveToken(data.token)
            sessionRepository.saveUserId(data.user.id)
        }.onFailure{
            sessionRepository.saveToken("")
            sessionRepository.saveUserId("")
        }

        return result

    }

}