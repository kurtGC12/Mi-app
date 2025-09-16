package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.app.composable.AppNav
import com.example.app.interfaces.*
import com.example.app.prefs.LanguagePrefs
import com.example.app.untils.LocalHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.app.ui.theme.MaterialTheme as MaterialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        runBlocking {
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
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewApp () {
    MaterialTheme {
        LoginApp(onGoRegister = {}, onGoForgot = {}, onLoginSuccess = {})

    }

}