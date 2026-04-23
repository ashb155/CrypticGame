package com.example.crypticgame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.crypticgame.ui.components.CrypticButton
import com.example.crypticgame.ui.components.TypeText
import com.example.crypticgame.ui.theme.AccentPrimary
import com.example.crypticgame.ui.theme.BackgroundDark
import com.example.crypticgame.viewmodel.PuzzleViewModel

@Composable
fun PuzzleScreen(
    viewModel: PuzzleViewModel,
    onBack: () -> Unit
) {
    val puzzle by viewModel.puzzle.collectAsState()
    val terminalOutput by viewModel.terminalOutput.collectAsState()
    var userInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        puzzle?.let { currentPuzzle ->
            TypeText(
                fullText = "/// ${currentPuzzle.title} ///",
                style = MaterialTheme.typography.titleLarge.copy(color = AccentPrimary)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = currentPuzzle.content,
                color = AccentPrimary.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(40.dp))


            if (!currentPuzzle.assetRef.isNullOrBlank()) {
                CrypticButton(
                    label = "./download_artefact",
                    onClick = {
                        // TODO: Handle opening the artifact
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        Text(
            text = terminalOutput,
            color = AccentPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "root@kryptiko:~# ", color = AccentPrimary)
            BasicTextField(
                value = userInput,
                onValueChange = { userInput = it },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = AccentPrimary),
                cursorBrush = SolidColor(AccentPrimary),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (userInput.isNotBlank()) {
                            viewModel.submitAnswer(userInput)
                            userInput = ""
                        }
                    }
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}