package com.example.tareas.src.core.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {
    val TOKEN = stringPreferencesKey("token")
    val ID_USER = stringPreferencesKey("user_id")
}