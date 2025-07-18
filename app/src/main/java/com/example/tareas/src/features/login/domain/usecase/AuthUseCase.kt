package com.example.tareas.src.features.login.domain.usecase

import com.example.tareas.src.features.login.domain.models.AuthRequest
import com.example.tareas.src.features.login.domain.models.AuthResponse
import com.example.tareas.src.features.login.domain.repository.AuthRepository
import com.example.tareas.src.features.login.domain.repository.SessionRepository


class AuthUseCase(
    private val repository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    suspend fun invoke(request: AuthRequest): Result<AuthResponse> {
        val result = repository.login(request)

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