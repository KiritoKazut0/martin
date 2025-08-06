package com.example.tareas.src.core.network.http

import com.example.tareas.src.core.datastore.DataStoreManager
import com.example.tareas.src.core.network.http.interceptor.AddTokenInterceptor
import com.example.tareas.src.core.network.http.interceptor.TokenCaptureInterceptor
import com.example.tareas.src.core.network.http.interceptor.provideLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
//    private const val BaseUrl = "https://tasks.wildroid.space"
    private const val BaseUrl = "https://fxrq4n51-5000.usw3.devtunnels.ms"
    private const val TIMEOUT = 20L

    private var retrofit: Retrofit? =  null
    private var dataStoreManager: DataStoreManager? = null

    fun init(dataStore : DataStoreManager, extraInterceptors: List<Interceptor> = emptyList()) {
        dataStoreManager = dataStore
        if (retrofit == null) {
            synchronized(this) {
                if (retrofit == null) {
                    retrofit = buildRetrofit(extraInterceptors)
                }
            }
        }
    }

    fun <T> getService(serviceClass: Class<T>): T {
        requireNotNull(retrofit) { "RetrofitClient no ha sido inicializado. Llama a init() primero." }
        return retrofit!!.create(serviceClass)
    }

    private fun buildRetrofit(extraInterceptors: List<Interceptor>): Retrofit {
        val client = buildHttpClient(extraInterceptors)

        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buildHttpClient(extraInterceptors: List<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(AddTokenInterceptor(requireNotNull(dataStoreManager)))
            .addInterceptor(TokenCaptureInterceptor(requireNotNull(dataStoreManager)))
            .addInterceptor(provideLoggingInterceptor())
            .apply {
                extraInterceptors.forEach { addInterceptor(it) }
            }
            .build()
    }

}