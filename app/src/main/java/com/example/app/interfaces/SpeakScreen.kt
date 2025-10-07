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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakScreen(
    onSettings: () -> Unit,
    onBack: () -> Unit,


) {

    val context = LocalContext.current
    var speechText by remember { mutableStateOf("presione el botón y hable") }
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
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
    val recognizerListener = object : RecognitionListener {
        override fun onBeginningOfSpeech() {
            speechText = "Escuchando..........."
        }

        override fun onBufferReceived(p0: ByteArray?) {
            Log.d("onBufferReceived: ", "Buffer recibido")
        }

        override fun onEndOfSpeech() {
            speechText = "Procesando..........."
        }

        override fun onError(p0: Int) {
            speechText = "Error al reconocer la voz"
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
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.d("onRmsChanged: ", "Nivel de ruido  no detectado")
        }
    }
    speechRecognizer.setRecognitionListener(recognizerListener)


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

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 2.dp,
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = speechText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }


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
                    Text("Presionar y hablar")
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



