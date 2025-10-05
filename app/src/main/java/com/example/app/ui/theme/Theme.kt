package com.example.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.app.prefs.AppPrefs
import androidx.compose.material3.MaterialTheme as M3Theme



//Colores de modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = PrimarioOscuro,
    secondary = SecundarioOscuro,
    tertiary = TerciarioOscuro,
    background = Color.Black,
    surface = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)
// Colores de modo claro
private val LightColorScheme = lightColorScheme(
    primary = PrimarioClaro,
    secondary = SecundarioClaro,
    tertiary = TerciarioClaro
)

@Composable
fun MaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val useDark = AppPrefs.darkMode || darkTheme

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        useDark -> DarkColorScheme
        else -> LightColorScheme
    }

    M3Theme(
        colorScheme = colorScheme,
        typography = scaledTypography(AppPrefs.fontScale),
        content = content
    )
}