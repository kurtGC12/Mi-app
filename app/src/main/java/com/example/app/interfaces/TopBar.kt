package com.example.app.interfaces


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarColors
import com.example.app.ui.theme.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


@SuppressLint("SuspiciousIndentation")
@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showBack: Boolean,
    onBack: (() -> Unit)? = null,
    onSettings: (() -> Unit)? = null,
    onMenu: (() -> Unit)? = null,
    isDarkMode: Boolean = false,
    lightColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color(0xFF2553C7), // color modo claro
        titleContentColor = Color.White,
        navigationIconContentColor = Color.White,
        actionIconContentColor = Color.White
    ),
    darkColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor =  Color(0xFF2553C7),    // color modo oscuro
        titleContentColor = Color(0xFFFCFCFC),
        navigationIconContentColor = Color(0xFFF8F8F8),
        actionIconContentColor = Color(0xFFFFFFFF)
    )
) {
    val colors = if (isDarkMode) darkColors else lightColors

        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                if (showBack && onBack != null) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás"
                        )
                    }
                }
            },
            actions = {
                if (onSettings != null) {
                    IconButton(onClick = onSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings, contentDescription = "Ajustes"
                        )
                    }
                }
                if (onMenu != null) {
                    IconButton(onClick = onMenu) {
                        Icon(
                            imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones"
                        )
                    }
                }
            },
            colors = colors
        )
    }


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    var dark by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface {
            Column {
                TopBar(
                    title = "Modo Claro",
                    showBack = true,
                    onBack = {},
                    onSettings = {},
                    onMenu = {},
                    isDarkMode = false
                )
                TopBar(
                    title = "Modo Oscuro",
                    showBack = true,
                    onBack = {},
                    onSettings = {},
                    onMenu = {},
                    isDarkMode = true
                )
            }
        }
    }
}