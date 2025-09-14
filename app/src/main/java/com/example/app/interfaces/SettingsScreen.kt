package com.example.app.interfaces


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.prefs.AppPrefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.MaterialTheme
import com.example.app.ui.theme.PrimarioClaro
import com.example.app.ui.theme.SecundarioClaro


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    var dark by remember { mutableStateOf(AppPrefs.darkMode) }
    var scale by remember { mutableStateOf(AppPrefs.fontScale) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Configuración",
                showBack = true,
                onBack = onBack,
                centered = true
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Modo nocturno
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Modo nocturno", style = MaterialTheme.typography.titleLarge)
                    Text(
                        "Mejora legibilidad para baja visión y ambientes con poca luz.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(checked = dark, onCheckedChange = {
                    dark = it
                    AppPrefs.darkMode = it
                })
            }

            // Tamaño de fuente
            Column {
                Text("Tamaño de fuente", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            scale = (scale - 0.05f).coerceIn(0.85f, 1.40f)
                            AppPrefs.fontScale = scale
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary,

                        )
                    ) { Text("a-") }

                    Text("${(scale * 100).toInt()}%", style = MaterialTheme.typography.titleLarge)

                    Button(
                        onClick = {
                            scale = (scale + 0.05f).coerceIn(0.85f, 1.40f)
                            AppPrefs.fontScale = scale
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) { Text("A+") }

                    TextButton(onClick = {
                        scale = 1.0f
                        AppPrefs.fontScale = 1.0f
                    }) { Text("Restablecer") }
                }

                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Vista previa", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Este es un ejemplo de texto con el tamaño seleccionado.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Ajusta a- / A+ para ver el efecto hasta que te resulte cómodo.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Settings Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen(onBack = { })
    }
}

@Preview(
    name = "Settings - Dark Mode",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsScreenDarkPreview() {
    MaterialTheme(darkTheme = true) {
        SettingsScreen(onBack = { })
    }
}