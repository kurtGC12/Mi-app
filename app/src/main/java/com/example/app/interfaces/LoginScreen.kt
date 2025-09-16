package com.example.app.interfaces




import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.untils.Registros
import com.airbnb.lottie.compose.*
import com.example.app.R
import com.example.app.ui.theme.MaterialTheme




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginApp(
    onGoRegister: () -> Unit,
    onGoForgot: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var correo by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var errors by remember { mutableStateOf<List<String>>(emptyList()) }


    Scaffold(
        topBar = { TopBar(title = "Iniciar sesión",
            showBack = false , onSettings = {},


            ) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(30.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(100.dp))
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.ee_gs1))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true,
                speed = 1.0f)
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(180.dp)
            )
        Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                placeholder = { Text("tucorreo@gmail.com") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                placeholder = { Text("********") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (errors.isNotEmpty()) { Spacer(Modifier.height(8.dp))
                errors.forEach { msg ->
                    Text(
                        text = "• $msg",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val res = Registros.autenticar(correo.trim(), password)
                    if (res.success) {
                        errors = emptyList()
                        onLoginSuccess() } else {
                        errors = res.errors }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ){
                Text("Iniciar sesión")
            }
            Spacer(Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TextButton(onClick =( onGoForgot),colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Olvidé mi contraseña")
                }
                TextButton(onClick = (onGoRegister),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ), modifier = Modifier.weight(1f)
                ) {
                    Text("Crear cuenta")
                }

            }
            Spacer(Modifier.height(20.dp))

            GoogleSignInButton {
                // Por ahora solo un print:
                println("Botón Google para iniciar sesion")
            }
        }
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )

    ) {
        Icon(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Google",
            modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.width(8.dp))
            Text("Iniciar con Google")
        }
    }
}

@Preview(name = "Login", showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    MaterialTheme {
        LoginApp(
            onGoRegister = {},
            onGoForgot = {},
            onLoginSuccess = {}
        )
    }
}