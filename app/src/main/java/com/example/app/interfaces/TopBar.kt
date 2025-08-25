package com.example.app.interfaces

import android.view.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface


@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showBack: Boolean,
    onBack: (() -> Unit)? = null,
    onSettings: (() -> Unit)? = null,
    onMenu: (() -> Unit)? = null ,
    centered: Boolean = false
    )
{

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
            }
        )
    }


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    MaterialTheme {
        Surface {
            TopBar(
                title = "Pantalla Principal",
                showBack = true,
                onBack = {},
                onSettings = {},
                onMenu = {},
                centered = true
            )
        }
    }
}