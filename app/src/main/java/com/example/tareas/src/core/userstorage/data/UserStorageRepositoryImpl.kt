package com.example.tareas.src.core.userstorage.data

import com.example.tareas.src.core.datastore.DataStoreManager
import com.example.tareas.src.core.datastore.PreferencesKey
import com.example.tareas.src.core.userstorage.domain.UserStorageRepository

class UserStorageRepositoryImpl(
    private val dataStore : DataStoreManager
): UserStorageRepository {
        override suspend fun getUserId(): String? = dataStore.getKey(PreferencesKey.ID_USER)
}