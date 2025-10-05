package com.example.app.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object LanguagePrefs {
    private val KEY_LANG = stringPreferencesKey("app_language")

    fun flowLanguage(context: Context): Flow<String> =
        context.dataStore.data.map { it[KEY_LANG] ?: "es" }

    suspend fun setLanguage(context: Context, tag: String) {
        context.dataStore.edit { it[KEY_LANG] = tag }
    }
}