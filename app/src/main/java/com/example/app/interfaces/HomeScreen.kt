package com.example.app.interfaces


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.app.ui.theme.MaterialTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import com.example.app.R
import com.example.app.prefs.AppearancePrefs
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(
    onLogout: () -> Unit,
    onSettings: () -> Unit,
    onSpeak: () -> Unit,
    onText: () -> Unit,
    onGeo: () -> Unit,
    onHome: () -> Unit
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val nombre by AppearancePrefs.flowNombre(context).collectAsState(initial = "")
    val correo by AppearancePrefs.flowCorreo(context).collectAsState(initial = "")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.width(300.dp)
            ) {
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    Text(
                        text = nombre ,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = correo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(12.dp))
                }
                Divider()

                DrawerItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = "Principal",
                    selected = true
                ) { scope.launch { drawerState.close() }
                    onHome() }

                DrawerItem(
                    icon = { Icon(Icons.Default.Place, null) },
                    label = "Buscar Dispositivo"
                ) { scope.launch { drawerState.close() }
                    onGeo()}


                Spacer(Modifier.height(8.dp))
                TextButton(
                    onClick = onLogout,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) { Text("Cerrar sesión") }
                Spacer(Modifier.height(12.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Principal",
                    showBack = false,
                    onSettings = onSettings,
                    onMenu = { scope.launch { drawerState.open() } }
                )
            }
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

                MenuItemCard(
                    title = "Reconocimiento de voz ",
                    subtitle = "Habla para que TextReader lo reciba y lo lea ",
                    icon = Icons.Outlined.Mic,
                    onClick = onSpeak
                )
                Spacer(Modifier.height(24.dp))
                MenuItemCard(
                    title = "Escribir o Pegar texto ",
                    subtitle = "Introduce o pega un texto para escuchar ",
                    icon = Icons.Outlined.MenuBook,
                    onClick =  onText
                )
                Spacer(Modifier.height(24.dp))
                MenuItemCard(
                    title = "Escanear Documentos ",
                    subtitle = "PROXIMAMENTE ",
                    icon = Icons.Default.CameraAlt,
                    onClick = onSpeak
                )

            }

        }
    }
}

@Composable
private fun DrawerItem(
    icon: @Composable () -> Unit,
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = icon,
        label = { Text(label) },
        selected = selected,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Composable
private fun MenuItemCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val cardBg = Color(0xFF213A6B)
    val iconBg = Color(0xFF273EB6)
    val iconTint = Color(0xFFFFFFFF)

    val interaction = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(cardBg)
            .clickable(
                interactionSource = interaction,
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconTint)
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
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
            HomeApp(onLogout = { }, onSettings = { }, onSpeak ={ }, onText = {}, onGeo = {}, onHome = {})
        }
    }
}