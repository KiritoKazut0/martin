package com.example.tareas.src.core.userstorage.domain

interface UserStorageRepository {
    suspend fun getUserId(): String?
}