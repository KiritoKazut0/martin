package com.example.tareas.src.features.tasks.di


import com.example.tareas.src.core.network.http.RetrofitHelper
import com.example.tareas.src.core.userstorage.data.UserStorageRepositoryImpl
import com.example.tareas.src.core.userstorage.domain.UserStorageRepository
import com.example.tareas.src.features.login.di.AppModule
import com.example.tareas.src.features.tasks.data.datasource.remote.TaskService
import com.example.tareas.src.features.tasks.data.repository.TaskRepositoryImpl
import com.example.tareas.src.features.tasks.domain.repository.TaskRepository
import com.example.tareas.src.features.tasks.domain.usecase.CreateTaskUseCase
import com.example.tareas.src.features.tasks.domain.usecase.DeleteTaskUseCase
import com.example.tareas.src.features.tasks.domain.usecase.ListTaskUseCase
import com.example.tareas.src.features.tasks.domain.usecase.UpdateTaskUseCase


object AppModule {
    private val dataStoreManager = AppModule.dataStoreManager

    private val taskService: TaskService by lazy {
        RetrofitHelper.getService(TaskService::class.java)
    }


    private val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(taskService)
    }


     val userStorageRepository: UserStorageRepository by lazy {
        UserStorageRepositoryImpl(dataStoreManager)
    }

    val createTaskUseCase: CreateTaskUseCase by lazy {
        CreateTaskUseCase(taskRepository)
    }
    val listTaskUseCase: ListTaskUseCase by lazy {
        ListTaskUseCase(taskRepository)
    }
    val updateTaskUseCase: UpdateTaskUseCase by lazy {
        UpdateTaskUseCase(taskRepository)
    }
    val deleteTaskUseCase: DeleteTaskUseCase by lazy {
        DeleteTaskUseCase(taskRepository)
    }

}