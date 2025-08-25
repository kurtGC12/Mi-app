package com.example.app.interfaces


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.app.interfaces.TopBar


@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    Scaffold { _ ->
        Button(onClick = onBack) { Text("Volver") }
    }
}
