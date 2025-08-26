package com.example.app.interfaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.app.interfaces.TopBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(onLogout: () -> Unit) {
    Scaffold(
        topBar = { TopBar(title = "Principal", showBack = false) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("TextReader", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onLogout) { Text("Cerrar sesión") }
        }
    }
}


@Preview(
    name = "Home ",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_6
)
@Composable
fun HomeAppPreview() {
    MaterialTheme {
        Surface {
            HomeApp(onLogout = { /* noop */ })
        }
    }
}

