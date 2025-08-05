package com.example.tareas.src.core.di

import androidx.room.Room
import com.example.tareas.src.core.appcontext.AppContextHolder
import com.example.tareas.src.core.network.local.database.TaskDatabase

object RoomModule {

    private val dbInstance: TaskDatabase by lazy {
        Room.databaseBuilder(
            AppContextHolder.get(),
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    fun getTaskDao() = dbInstance.getTaskDaou()

}