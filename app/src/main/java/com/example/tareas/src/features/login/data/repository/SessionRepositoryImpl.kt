package com.example.tareas.src.features.login.data.repository

import com.example.tareas.src.core.datastore.DataStoreManager
import com.example.tareas.src.core.datastore.PreferencesKey
import com.example.tareas.src.features.login.domain.repository.SessionRepository


class SessionRepositoryImpl(
    private val dataStoreToken: DataStoreManager
): SessionRepository {
    override suspend fun getToken(): String? = dataStoreToken.getKey(PreferencesKey.TOKEN)
    override suspend fun saveToken(token: String) = dataStoreToken.saveKey(PreferencesKey.TOKEN, token)

    override suspend fun getUserId(): String? = dataStoreToken.getKey(PreferencesKey.ID_USER)
    override suspend fun saveUserId(idUser: String) = dataStoreToken.saveKey(PreferencesKey.ID_USER, idUser)
}