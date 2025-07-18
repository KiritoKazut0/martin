package com.example.tareas.src.features.tasks.data.datasource.remote

import com.example.tareas.src.features.tasks.data.models.CreateTaskDto
import com.example.tareas.src.features.tasks.data.models.DeleteTaskDto
import com.example.tareas.src.features.tasks.data.models.TaskDto
import com.example.tareas.src.features.tasks.data.models.UpdateTaskDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskService {
    @GET("/api/notes/{idUser}")
    suspend fun listTask(@Path("idUser") idUser: String): List<TaskDto>

    @POST("/api/notes/")
    suspend fun createTask(@Body request: CreateTaskDto): TaskDto

    @PUT("/api/notes/{id}")
    suspend fun updateTask(@Path("id") id: String, @Body request: UpdateTaskDto): TaskDto

    @DELETE("/api/notes/{id}")
    suspend fun deleteTask(@Path("id") id: String): DeleteTaskDto
}