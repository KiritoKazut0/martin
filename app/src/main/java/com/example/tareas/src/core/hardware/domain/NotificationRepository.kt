package com.example.tareas.src.core.hardware.domain

interface NotificationRepository {
    fun activeNotification(idTask: String, taskTitle: String)
}