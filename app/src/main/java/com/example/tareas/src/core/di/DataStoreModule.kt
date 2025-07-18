package com.example.tareas.src.core.di

import com.example.tareas.src.core.appcontext.AppContextHolder
import com.example.tareas.src.core.datastore.DataStoreManager

object DataStoreModule {
    val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager(AppContextHolder.get())
    }
}