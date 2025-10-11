package com.example.app.interfaces

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.Manifest
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakScreen(
    onSettings: () -> Unit,
    onBack: () -> Unit


) {

    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var speechText by remember { mutableStateOf("presione el botón y hable") }
    var isListening by remember { mutableStateOf(false) }


    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora…")
        }
    }


    val permissionToRecordAudio = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            speechRecognizer.startListening(recognizerIntent)
        } else {
            Toast.makeText(context, "Permiso denegado para el microfono", Toast.LENGTH_SHORT).show()
        }
    }
    fun startOrRequestPermission() {
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO
        ) == PERMISSION_GRANTED

        if (hasPermission) {
            try {
                speechRecognizer.startListening(recognizerIntent)
            } catch (e: Exception) {
                speechText = "No se pudo iniciar el reconocimiento"
            }
        } else {
            permissionToRecordAudio.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
    val recognizerListener = object : RecognitionListener {
        override fun onBeginningOfSpeech() {
            speechText = "Escuchando..........."
            isListening = true
        }

        override fun onBufferReceived(p0: ByteArray?) {
            Log.d("onBufferReceived: ", "Buffer recibido")
        }

        override fun onEndOfSpeech() {
            speechText = "Procesando..........."
            isListening = false
        }

        override fun onError(p0: Int) {
            speechText = "Error al reconocer la voz"
            isListening = false
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
            Log.d("onEvent: ", "Evento recibido")
        }

        override fun onPartialResults(p0: Bundle?) {
            Log.d("onPartialResults: ", "Resultado parcial recibido")
        }

        override fun onReadyForSpeech(p0: Bundle?) {
            Log.d("onReadyForSpeech: ", "Listo para recibir voz")
        }

        override fun onResults(p0: Bundle?) {
            val matches = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            speechText = matches?.firstOrNull() ?: "No se pudo reconocer la voz"
            isListening = false
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.d("onRmsChanged: ", "Nivel de ruido  no detectado")
        }
    }
    speechRecognizer.setRecognitionListener(recognizerListener)

    DisposableEffect(Unit) {
        onDispose {
            try {
                speechRecognizer.stopListening()
                speechRecognizer.cancel()
            } catch (_: Exception) {}
            speechRecognizer.destroy()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Reconocimiento de voz",
                showBack = true,
                onBack = onBack,
                onSettings = onSettings
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                    Text(
                        text = speechText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )



                Button(
                    onClick = { permissionToRecordAudio.launch(Manifest.permission.RECORD_AUDIO) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .widthIn(min = 220.dp)
                        .height(56.dp)
                ) {
                    Text(if (isListening) "Escuchando…" else "Presionar y hablar")
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SpeakScreenPreview() {
    MaterialTheme {
        SpeakScreen(
            onSettings = {},
            onBack = {}
        )
    }
}



