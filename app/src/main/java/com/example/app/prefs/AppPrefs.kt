package com.example.app.prefs


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppPrefs {
    var darkMode by mutableStateOf(false)
    var fontScale by mutableStateOf(1.0f)
}

