package com.example.tareas.src.features.login.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tareas.src.features.login.domain.usecase.AuthUseCase


class LoginViewModelFactory (private val accessUseCase: AuthUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(accessUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}