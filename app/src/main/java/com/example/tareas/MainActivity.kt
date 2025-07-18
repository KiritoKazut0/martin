package com.example.tareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tareas.src.core.appcontext.AppContextHolder
import com.example.tareas.src.core.navegation.NavigationWrapper
import com.example.tareas.src.features.login.di.AppModule


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppModule.init(this)
        AppContextHolder.init(this)

        enableEdgeToEdge()
        setContent {
            NavigationWrapper()
        }
    }
}

