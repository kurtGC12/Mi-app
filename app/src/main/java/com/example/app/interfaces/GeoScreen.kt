package com.example.app.interfaces

import android.Manifest
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.launch
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.untils.LocationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoScreen(
    onSettings: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val finePermission = Manifest.permission.ACCESS_FINE_LOCATION
    val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION
    var lat by rememberSaveable { mutableStateOf<Double?>(null) }
    var lng by rememberSaveable { mutableStateOf<Double?>(null) }
    var accuracy by rememberSaveable { mutableStateOf<Float?>(null) }
    var status by rememberSaveable { mutableStateOf<String?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val granted = (perms[finePermission] == true) || (perms[coarsePermission] == true)
        if (granted) {
            status = "Permisos concedidos. Obteniendo ubicación…"
            scope.launch {
                try {
                    val loc = LocationHelper.getCurrentLocation(context)
                    if (loc != null) {
                        lat = loc.latitude
                        lng = loc.longitude
                        accuracy = loc.accuracy
                        status = "Ubicación obtenida."
                    } else {
                        status = "No se obtuvo ubicación (¿GPS desactivado?)."
                    }
                } catch (e: Exception) {
                    status = "Error: ${e.message}"
                }
            }
        } else {
            status = "Permisos denegados. No se puede obtener la ubicación."
        }
    }

    fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(context, finePermission) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(context, coarsePermission) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Buscar Dispositivo",
                showBack = true,
                onBack = onBack,
                onSettings = onSettings
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(Modifier.height(24.dp))
            Text("Ubicación GPS", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))


            Button(
                onClick = {
                    if (hasLocationPermission()) {
                        status = "Obteniendo ubicación…"
                        scope.launch {
                            try {
                                val loc = LocationHelper.getCurrentLocation(context)
                                if (loc != null) {
                                    lat = loc.latitude
                                    lng = loc.longitude
                                    accuracy = loc.accuracy
                                    status = "Ubicación obtenida."
                                } else {
                                    status = "No se obtuvo ubicación (¿GPS desactivado?)."
                                }
                            } catch (e: Exception) {
                                status = "Error: ${e.message}"
                            }
                        }
                    } else {

                        permissionLauncher.launch(arrayOf(finePermission, coarsePermission))
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Obtener ubicación")
            }

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Estado: ${status ?: "—"}")
                    Spacer(Modifier.height(8.dp))
                    Text("Latitud: ${lat?.toString() ?: "—"}")
                    Text("Longitud: ${lng?.toString() ?: "—"}")
                    Text("Precisión (m): ${accuracy?.toString() ?: "—"}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GeoScreenPreview() {
    MaterialTheme {
        GeoScreen(
            onSettings = {},
            onBack = {}
        )
    }
}
