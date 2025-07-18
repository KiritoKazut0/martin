package com.example.tareas.src.core.hardware.domain

interface VibratorRepository {
    fun vibrate(milliseconds: Long)
}