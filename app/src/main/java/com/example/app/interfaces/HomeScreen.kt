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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.app.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(
    onLogout: () -> Unit,
    onSettings: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                }
                Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    Text("Violet Norman", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text("bayer_martin@yahoo.com", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Divider()

                DrawerItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = "Home",
                    selected = true
                ) { scope.launch { drawerState.close() } }

                DrawerItem(
                    icon = { Icon(Icons.Default.Star, null) },
                    label = "New collections"
                ) { scope.launch { drawerState.close() } }

                DrawerItem(
                    icon = { Icon(Icons.Default.Edit, null) },
                    label = "Editor’s Picks"
                ) { scope.launch { drawerState.close() } }



                DrawerItem(
                    icon = { Icon(Icons.Default.Notifications, null) },
                    label = "Notifications"
                ) { scope.launch { drawerState.close() } }



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
                Button(onClick = onLogout) { Text("Cerrar sesión") }
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
            HomeApp(onLogout = { }, onSettings = { })
        }
    }
}