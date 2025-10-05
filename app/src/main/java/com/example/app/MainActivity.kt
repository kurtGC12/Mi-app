package com.example.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.app.composable.AppNav
import com.example.app.interfaces.*
import com.example.app.prefs.LanguagePrefs
import com.example.app.untils.LocalHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.app.prefs.AppearancePrefs
import com.example.app.prefs.AppPrefs
import com.example.app.ui.theme.MaterialTheme as MaterialTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        runBlocking {
            AppPrefs.darkMode = AppearancePrefs.flowDarkMode(this@MainActivity).first()
            AppPrefs.fontScale = AppearancePrefs.flowFontScale(this@MainActivity).first()
            val tag = LanguagePrefs.flowLanguage(applicationContext).first()
            LocalHelper.applyLanguage(tag)
        }
        lifecycleScope.launch {
            LanguagePrefs.flowLanguage(applicationContext).collect { tag ->
                LocalHelper.applyLanguage(tag)
            }
        }
        setContent {
            MaterialTheme {
                AppNav()
                SpeechRecognitionApp()
            }
        }
    }
}

@Composable
fun SpeechRecognitionApp() {
    val context = LocalContext.current
    val speechText = remember { mutableStateOf("presione el botón y hable") }
    val recognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp () {
    MaterialTheme {
        LoginApp(onGoRegister = {}, onGoForgot = {}, onLoginSuccess = {}, onOpenSettings={})

    }

}