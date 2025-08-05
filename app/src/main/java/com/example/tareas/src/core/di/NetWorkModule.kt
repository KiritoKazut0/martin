package com.example.tareas.src.core.di


import com.example.tareas.src.core.appcontext.AppContextHolder
import com.example.tareas.src.core.network.NetworkManager


object NetworkModule {
    val networkManager: NetworkManager by lazy {
        NetworkManager(AppContextHolder.get())
    }
}
