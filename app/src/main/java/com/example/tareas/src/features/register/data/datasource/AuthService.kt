package com.example.tareas.src.features.register.data.datasource


import com.example.tareas.src.features.register.data.models.AuthRequestDto
import com.example.tareas.src.features.register.data.models.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: AuthRequestDto): AuthResponseDto
}