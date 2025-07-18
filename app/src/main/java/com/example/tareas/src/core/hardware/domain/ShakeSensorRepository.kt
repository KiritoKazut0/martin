package com.example.tareas.src.core.hardware.domain

interface ShakeSensorRepository {
    fun startListening(onShake: () -> Unit)
    fun stopListening()
}