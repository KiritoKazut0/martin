package com.example.tareas.src.features.tasks.presentation.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.tareas.src.core.datastore.DataStoreManager
import com.example.tareas.src.core.datastore.PreferencesKey
import com.example.tareas.src.core.di.HardwareModule
import com.example.tareas.src.core.hardware.domain.NotificationRepository
import com.example.tareas.src.core.network.NetworkManager
import com.example.tareas.src.features.tasks.di.AppModule
import com.example.tareas.src.features.tasks.domain.models.CreateTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SyncTaskService : Service() {

    private val CHANNEL_ID = "notify"

    private val networkManager: NetworkManager by lazy { AppModule.networkManager }
    private val localRepository = AppModule.localTaskRepository
    private val remoteTaskRepository = AppModule.taskRepository
    private val dataStore: DataStoreManager by lazy { AppModule.dataStoreManager }

    private val notificationManager: NotificationRepository by lazy {
        HardwareModule.notificationManager
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Sincronización de tareas")
            .setContentText("Servicio en ejecución")
            .setSmallIcon(android.R.drawable.ic_popup_sync)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(1, notification)

        CoroutineScope(Dispatchers.IO).launch {
            sendTasks()
            stopSelf()
        }

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Canal de sincronización de tareas",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private suspend fun sendTasks() {
        if (networkManager.isConnected()) {
            val idUser = dataStore.getKey(PreferencesKey.ID_USER)
            if (idUser != null) {
                val result = localRepository.list(idUser)

                if (result.isSuccess) {
                    val tasks = result.getOrNull()
                    Log.d("SyncTaskService", "Tareas encontradas: ${tasks?.size ?: 0}")
                    if (!tasks.isNullOrEmpty()) {
                        tasks.forEach { task ->
                            Log.d("SyncTaskService", "Tarea a sincronizar: ${task.title} - ${task.description}")

                            val createTask = CreateTask(
                                title = task.title,
                                description = task.description,
                                user_id = idUser
                            )

                            val response = remoteTaskRepository.create(createTask)
                            localRepository.delete(task.id)
                            if (response.isSuccess) {
                                notificationManager.activeNotification(
                                    idTask = task.id,
                                    taskTitle = "Tarea sincronizada: ${task.title}"
                                )
                            } else {
                                Log.e("SyncTaskService", "Error al sincronizar tarea: ${task.title}")
                            }
                        }
                    }
                } else {
                    Log.e("SyncTaskService", "Error al obtener tareas locales: ${result.exceptionOrNull()?.message}")
                }
            } else {
                Log.e("SyncTaskService", "idUser es null")
            }
        } else {
            Log.d("SyncTaskService", "No hay conexión a internet")
            notificationManager.activeNotification(
                idTask = "sync-fail",
                taskTitle = "Sin conexión: tareas no sincronizadas"
            )
        }
    }
}
