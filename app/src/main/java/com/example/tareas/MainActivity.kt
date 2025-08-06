package com.example.tareas

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tareas.src.core.appcontext.AppContextHolder
import com.example.tareas.src.core.navegation.NavigationWrapper
import com.example.tareas.src.features.login.di.AppModule
import com.example.tareas.src.features.tasks.presentation.receivers.ConnectivityReceiver


class MainActivity : ComponentActivity() {

    private lateinit var connectivityReceiver: ConnectivityReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppModule.init(this)
        AppContextHolder.init(this)

        connectivityReceiver = ConnectivityReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, filter)

        enableEdgeToEdge()
        setContent {
            NavigationWrapper()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectivityReceiver)
    }
}

