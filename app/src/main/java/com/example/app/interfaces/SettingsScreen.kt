package com.example.app.interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.prefs.AppPrefs
import com.example.app.prefs.AppearancePrefs
import com.example.app.prefs.LanguagePrefs
import kotlinx.coroutines.launch
import android.app.Activity
import androidx.compose.ui.graphics.Color
import com.example.app.ui.theme.MaterialTheme
import com.example.app.untils.LocalHelper
import androidx.compose.ui.res.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val activity = context as? Activity

    val dark by AppearancePrefs.flowDarkMode(context).collectAsState(initial = false)
    var scale by remember { mutableStateOf(AppPrefs.fontScale) }


    val currentLang by LanguagePrefs.flowLanguage(context).collectAsState(initial = "es")
    var selectedLang by remember(currentLang) { mutableStateOf(currentLang) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Configuración",
                showBack = true,
                onBack = onBack,
                isDarkMode = dark,
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
            //Modo oscuro
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Modo Oscuro", style = MaterialTheme.typography.titleLarge)
                    Text(
                        "Mejora legibilidad para baja visión y ambientes con poca luz.",
                        style = MaterialTheme.typography.bodyMedium)
                }
                Switch(
                    checked = dark,
                    onCheckedChange = { enabled ->
                        scope.launch { AppearancePrefs.setDarkMode(context, enabled) }
                    }
                )
            }

            //Tamaño de fuente
            Column {
                Text("Tamaño de fuente", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            scale = (scale - 0.05f).coerceIn(0.85f, 1.40f)
                            AppPrefs.fontScale = scale
                            scope.launch { AppearancePrefs.setFontScale(context, scale) }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = Color.White,)
                    ) { Text("a-") }

                    Text("${(scale * 100).toInt()}%", style = MaterialTheme.typography.titleLarge)
                    Button(
                        onClick = {
                            scale = (scale + 0.05f).coerceIn(0.85f, 1.40f)
                            AppPrefs.fontScale = scale
                            scope.launch { AppearancePrefs.setFontScale(context, scale) }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        )
                    ) { Text("A+") }

                    TextButton(
                        onClick = {
                            scale = 1.0f
                            AppPrefs.fontScale = 1.0f
                            scope.launch { AppearancePrefs.setFontScale(context, 1.0f) }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.White //
                        )
                    ) {
                        Text("Restablecer", color = Color.White)
                    }
                }

                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Vista previa", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Este es un ejemplo de texto con el tamaño seleccionado.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Ajusta a- / A+ para ver el efecto hasta que te resulte cómodo.",
                            style = MaterialTheme.typography.bodyMedium

                        )
                    }
                }
            }
// En otro momento (falta colocar los string de cada idioma )//
          /*
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Idioma de la aplicación", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Cambia el idioma de toda la app al instante.", style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))

                LanguageRow(
                    selected = selectedLang == "es",
                    label = "Español",
                    onClick = {
                        selectedLang = "es"
                        scope.launch {
                            LanguagePrefs.setLanguage(context, "es")
                            LocalHelper.applyLanguage("es")
                            activity?.recreate()
                        }
                    }
                )
                LanguageRow(
                    selected = selectedLang == "en",
                    label = "English",
                    onClick = {
                        selectedLang = "en"
                        scope.launch {
                            LanguagePrefs.setLanguage(context, "en")
                            LocalHelper.applyLanguage("en")
                            activity?.recreate()
                        }
                    }
                )
                LanguageRow(
                    selected = selectedLang == "pt",
                    label = "Português",
                    onClick = {
                        selectedLang = "pt"
                        scope.launch {
                            LanguagePrefs.setLanguage(context, "pt")
                            LocalHelper.applyLanguage("pt")
                            activity?.recreate()
                        }
                    }
                )
            }*/
        }
    }
}

@Composable
private fun LanguageRow(
    selected: Boolean,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
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
