package com.example.crypticgame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.R
import com.example.crypticgame.ui.components.CrypticButton
import com.example.crypticgame.ui.components.TypeText
import com.example.crypticgame.ui.theme.AccentPrimary
import com.example.crypticgame.ui.theme.BackgroundDark
import com.example.crypticgame.viewmodel.PuzzleViewModel

@Composable
fun PuzzleScreen(
    viewModel: PuzzleViewModel,
    onBack: () -> Unit,
    onNavigateNext: (Int) -> Unit
) {
    val puzzle by viewModel.puzzle.collectAsState()
    val terminalOutput by viewModel.terminalOutput.collectAsState()
    val isSolved by viewModel.isSolved.collectAsState()
    var userInput by remember { mutableStateOf("") }


    val dataFont = FontFamily(Font(R.font.vt323))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        puzzle?.let { currentPuzzle ->

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TypeText(
                    fullText = "/// ${currentPuzzle.title} ///",
                    style = MaterialTheme.typography.titleLarge.copy(color = AccentPrimary)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = currentPuzzle.content,
                color = AccentPrimary.copy(alpha = 0.9f),
                fontFamily = dataFont,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (currentPuzzle.hint.isNotBlank()) {
                Text(
                    text = currentPuzzle.hint,
                    color = AccentPrimary.copy(alpha = 0.7f),
                    fontFamily = dataFont,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            if (!currentPuzzle.assetRef.isNullOrBlank()) {
                CrypticButton(
                    label = "./download_artefact",
                    onClick = { }
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

        if (isSolved) {
            puzzle?.unlocksNext?.let { nextId ->
                CrypticButton(
                    label = "./proceed",
                    onClick = { onNavigateNext(nextId) }
                )
            }
        } else {
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
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}