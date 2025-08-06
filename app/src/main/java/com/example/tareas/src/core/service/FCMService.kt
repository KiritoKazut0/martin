package com.example.tareas.src.core.service

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.tareas.R
import com.example.tareas.src.features.tasks.di.AppModule

class FcmService : FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendTokenToBackend(token)
    }

    private fun sendTokenToBackend(token: String){
        println("⚙️ Enviando token al backend: $token")
        val request = FcmTokenRequest(token)
        AppModule.fcmService.sendFcmToken(request).enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    println("Token enviado correctamente")
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    private fun showNotification(message: RemoteMessage) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(this, "notify")
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
    }
}