package com.example.tareas.src.core.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class FcmTokenRequest(
    val fcmToken: String
)

interface FcmServiceApi {
    @POST("api/devices/registro-dispositivo")
    fun sendFcmToken(@Body tokenRequest: FcmTokenRequest): Call<Void>
}