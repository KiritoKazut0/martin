package com.example.tareas.src.features.register.di


import com.example.tareas.src.core.network.http.RetrofitHelper
import com.example.tareas.src.features.login.di.AppModule
import com.example.tareas.src.features.register.data.datasource.AuthService
import com.example.tareas.src.features.register.data.repository.AuthRepositoryImpl
import com.example.tareas.src.features.register.data.repository.SessionRepositoryImpl
import com.example.tareas.src.features.register.domain.repository.AuthRepository
import com.example.tareas.src.features.register.domain.repository.SessionRepository
import com.example.tareas.src.features.register.domain.usecase.AuthUseCase

object AppModule  {
    val dataStoreManager = AppModule.dataStoreManager

    private val sessionRepository: SessionRepository by lazy {
        SessionRepositoryImpl(dataStoreManager)
    }

    private val authService: AuthService by lazy {
        RetrofitHelper.getService(AuthService::class.java)
    }

    private val repositoryAuth: AuthRepository by lazy {
        AuthRepositoryImpl(authService)
    }

    val registerUseCase: AuthUseCase by lazy {
        AuthUseCase(
            repository = repositoryAuth,
            sessionRepository = sessionRepository
        )
    }

}