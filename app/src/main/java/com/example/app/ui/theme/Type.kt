package com.example.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** Escala un valor en sp con el factor de fuente elegido */
private fun scaled(baseSp: Int, scale: Float) = (baseSp * scale).sp
    /** Tipografía escalable según preferencia del usuario */
    fun scaledTypography(scale: Float): Typography {
        val safe = scale.coerceIn(0.85f, 1.40f)

        return Typography(
            // Usados en tus pantallas
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = scaled(16, safe),
                lineHeight = scaled(24, safe),
                letterSpacing = 0.5.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = scaled(14, safe),
                lineHeight = scaled(20, safe),
                letterSpacing = 0.25.sp
            ),
            bodySmall = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = scaled(12, safe),
                lineHeight = scaled(16, safe),
                letterSpacing = 0.4.sp
            ),
            titleLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = scaled(22, safe),
            ),
            headlineMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = scaled(28, safe),
                lineHeight = scaled(32, safe),
            ),
            labelLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = scaled(14, safe),
                letterSpacing = 0.1.sp
            )
        )
    }