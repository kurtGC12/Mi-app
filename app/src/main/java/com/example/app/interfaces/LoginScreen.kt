package com.example.app.interfaces



import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.app.interfaces.TopBar



@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onGoRegister: () -> Unit,
    onGoForgot: () -> Unit
) {
    Scaffold { _ ->
        Button(onClick = onGoRegister) { Text("Ir a Registro") }
        Button(onClick = onGoForgot) { Text("Olvidé contraseña") }
    }
}
