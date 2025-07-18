package com.example.tareas.src.core.network.http.interceptor

import com.example.tareas.src.core.datastore.DataStoreManager
import com.example.tareas.src.core.datastore.PreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

class TokenCaptureInterceptor(
    private val dataStore: DataStoreManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val authHeader = response.header("Authorization")

        if (!authHeader.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                dataStore.saveKey(PreferencesKey.TOKEN, authHeader)
            }
        }

        return response
    }
}