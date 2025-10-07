package com.example.app.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "settings")

object AppearancePrefs {
    private val KEY_DARK = booleanPreferencesKey("dark_mode")
    private val KEY_FONT = floatPreferencesKey("font_scale")
    private val KEY_NOMBRE = stringPreferencesKey("user_nombre")
    private val KEY_CORREO = stringPreferencesKey("user_correo")

    fun flowDarkMode(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_DARK] ?: false }

    fun flowFontScale(context: Context): Flow<Float> =
        context.dataStore.data.map { it[KEY_FONT] ?: 1.0f }

    suspend fun setDarkMode(context: Context, enabled: Boolean) {
        context.dataStore.edit { it[KEY_DARK] = enabled }
    }

    suspend fun setFontScale(context: Context, scale: Float) {
        val safe = scale.coerceIn(0.85f, 1.40f)
        context.dataStore.edit { it[KEY_FONT] = safe }

    }
    suspend fun setUsuario(context: Context, nombre: String, correo: String) {
        context.dataStore.edit {
            it[KEY_NOMBRE] = nombre
            it[KEY_CORREO] = correo
        }
    }
    fun flowNombre(context: Context): Flow<String> =
        context.dataStore.data.map { it[KEY_NOMBRE] ?: "" }

    fun flowCorreo(context: Context): Flow<String> =
        context.dataStore.data.map { it[KEY_CORREO] ?: "" }

    suspend fun limpiarUsuario(context: Context) {
        context.dataStore.edit { it.clear() }
    }

}