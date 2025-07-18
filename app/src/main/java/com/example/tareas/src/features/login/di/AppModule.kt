package com.example.tareas.src.features.login.di

import android.content.Context
import com.example.tareas.src.core.datastore.DataStoreManager
import com.example.tareas.src.core.network.http.RetrofitHelper
import com.example.tareas.src.features.login.data.datasource.AuthService
import com.example.tareas.src.features.login.data.repository.AuthRepositoryImpl
import com.example.tareas.src.features.login.data.repository.SessionRepositoryImpl
import com.example.tareas.src.features.login.domain.repository.AuthRepository
import com.example.tareas.src.features.login.domain.repository.SessionRepository
import com.example.tareas.src.features.login.domain.usecase.AuthUseCase

object AppModule {

     lateinit var appContext: Context
     lateinit var dataStoreManager: DataStoreManager

    private var isInitialized = false

    fun init(context: Context) {
        if (!isInitialized) {
            appContext = context.applicationContext
            dataStoreManager = DataStoreManager(appContext)
            RetrofitHelper.init(dataStoreManager)
            isInitialized = true
        }
    }

    private val sessionRepository: SessionRepository by lazy {
        SessionRepositoryImpl(dataStoreManager)
    }

    private val authService: AuthService by lazy {
        RetrofitHelper.getService(AuthService::class.java)
    }

    private val repositoryAuth: AuthRepository by lazy {
        AuthRepositoryImpl(authService)
    }

    val loginUseCase: AuthUseCase by lazy {
        AuthUseCase(
            repository = repositoryAuth,
            sessionRepository = sessionRepository
        )
    }


}