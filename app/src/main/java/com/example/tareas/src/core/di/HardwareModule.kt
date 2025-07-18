package com.example.tareas.src.core.di

import android.content.Context
import com.example.tareas.src.core.appcontext.AppContextHolder
import com.example.tareas.src.core.hardware.data.NotificationManager
import com.example.tareas.src.core.hardware.data.ShakeDetectorManager
import com.example.tareas.src.core.hardware.data.VibratorRepositoryImpl
import com.example.tareas.src.core.hardware.domain.NotificationRepository
import com.example.tareas.src.core.hardware.domain.ShakeSensorRepository
import com.example.tareas.src.core.hardware.domain.VibratorRepository

object HardwareModule {
    val vibratorManager: VibratorRepository by lazy {
        VibratorRepositoryImpl(AppContextHolder.get())
    }

    fun provideShakeDetector(context: Context): ShakeSensorRepository {
        return ShakeDetectorManager(context)
    }

    val notificationManager: NotificationRepository by lazy {
        NotificationManager(AppContextHolder.get())
    }

}