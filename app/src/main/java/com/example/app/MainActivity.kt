package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.composable.AppNav
import com.example.app.interfaces.*
import com.example.app.ui.theme.MaterialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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