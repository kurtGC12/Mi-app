package com.example.app.interfaces



import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.app.ui.theme.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import java.util.Locale
import android.speech.tts.TextToSpeech


class TtsManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context, this)
    var isReady: Boolean = false
        private set

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val resCL = tts?.setLanguage(Locale.forLanguageTag("es-CL")) ?: TextToSpeech.LANG_NOT_SUPPORTED
            val okCL = resCL != TextToSpeech.LANG_MISSING_DATA && resCL != TextToSpeech.LANG_NOT_SUPPORTED

            val ready = if (!okCL) {
                val resES = tts?.setLanguage(Locale.forLanguageTag("es-ES")) ?: TextToSpeech.LANG_NOT_SUPPORTED
                resES != TextToSpeech.LANG_MISSING_DATA && resES != TextToSpeech.LANG_NOT_SUPPORTED
            } else true

            isReady = ready
        } else {
            isReady = false
        }
    }

    fun speak(text: String, rate: Float = 1.0f, pitch: Float = 1.0f) {
        val engine = tts ?: return
        if (!isReady || text.isBlank()) return
        engine.setSpeechRate(rate.coerceIn(0.5f, 2.0f))
        engine.setPitch(pitch.coerceIn(0.5f, 2.0f))
        engine.stop()
        engine.speak(text.trim(), TextToSpeech.QUEUE_FLUSH, null, "UTT_SIMPLE")
    }
    fun stop() { tts?.stop() }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isReady = false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextScreen(
    modifier: Modifier = Modifier,
    onSettings: () -> Unit,
    onBack: () -> Unit,
    ) {
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val ttsManager = remember { TtsManager(context) }

    DisposableEffect(Unit) {
        onDispose { ttsManager.shutdown() }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Escribir o Pegar texto",
                showBack = true,
                onBack = onBack,
                onSettings = onSettings,
            )
        }

    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = {
                        val clip = clipboard.getText()
                        if (clip != null) {
                            text = TextFieldValue(clip.text)
                        }
                    }
                ) { Text("Pegar") }

                OutlinedButton(
                    onClick = {
                        clipboard.setText(AnnotatedString(text.text))
                    },
                    enabled = text.text.isNotEmpty()
                ) { Text("Copiar todo") }

                TextButton(
                    onClick = { text = TextFieldValue("") },
                    enabled = text.text.isNotEmpty()
                ) { Text("Limpiar") }
            }

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true),
                placeholder = { Text("Escribe o pega aquí") },
                minLines = 8,
                maxLines = Int.MAX_VALUE,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) { Button(onClick = { ttsManager.speak(text.text) },
                    enabled = ttsManager.isReady && text.text.isNotBlank(),
                    modifier = Modifier.weight(1f)
                )
                { Text("Escuchar")
                }

                OutlinedButton(
                    onClick = { ttsManager.stop() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Detener")
                }
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextScreenPreview() {
    MaterialTheme {
        TextScreen(
            onBack = { }, onSettings = {}
        )
    }
}