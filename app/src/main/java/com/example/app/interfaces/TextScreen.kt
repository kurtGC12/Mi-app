package com.example.app.interfaces



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

    Scaffold(
        topBar = {
            TopBar(
                title = "Principal",
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
                OutlinedButton(
                    onClick = {
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