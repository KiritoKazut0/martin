package com.example.tareas.src.features.login.data.datasource


import com.example.tareas.src.features.login.data.models.AuthRequestDto
import com.example.tareas.src.features.login.data.models.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: AuthRequestDto): AuthResponseDto
}