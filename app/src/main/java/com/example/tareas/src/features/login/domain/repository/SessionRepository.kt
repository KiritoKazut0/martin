package com.example.tareas.src.features.login.domain.repository

interface SessionRepository {
        suspend fun getToken(): String?
        suspend fun saveToken(token: String)

        suspend fun getUserId(): String?
        suspend fun saveUserId(idUser: String)
}