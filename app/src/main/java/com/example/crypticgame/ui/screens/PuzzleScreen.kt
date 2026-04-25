package com.example.crypticgame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.ui.components.CrypticButton
import com.example.crypticgame.ui.components.TypeText
import com.example.crypticgame.ui.theme.AccentPrimary
import com.example.crypticgame.ui.theme.BackgroundDark
import com.example.crypticgame.ui.theme.ScanlineGreen
import com.example.crypticgame.viewmodel.PuzzleViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .drawBehind {
                var y = 0f
                while (y < size.height) {
                    drawLine(
                        color = ScanlineGreen,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1f
                    )
                    y += 4f
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Column(
                modifier = Modifier.padding(top = 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                puzzle?.let { currentPuzzle ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "◈",
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = AccentPrimary.copy(alpha = 0.5f),
                                fontSize = 22.sp
                            )
                        )
                        Spacer(Modifier.width(8.dp))
                        TypeText(
                            fullText = currentPuzzle.title.uppercase(),
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = AccentPrimary,
                                fontSize = 38.sp
                            )
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "◈",
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = AccentPrimary.copy(alpha = 0.5f),
                                fontSize = 22.sp
                            )
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterHorizontally),
                        thickness = 1.dp,
                        color = AccentPrimary.copy(alpha = 0.4f)
                    )

                    Spacer(Modifier.height(32.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .hackerCorners(
                                brush = SolidColor(AccentPrimary),
                                length = 32.dp,
                                strokeWidth = 4.dp
                            )
                            .background(AccentPrimary.copy(alpha = 0.04f))
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentPuzzle.content,
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = AccentPrimary,
                                fontSize = 34.sp,
                                lineHeight = 44.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (currentPuzzle.hint.isNotBlank()) {
                        Spacer(Modifier.height(32.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                thickness = 0.5.dp,
                                color = AccentPrimary.copy(alpha = 0.2f)
                            )
                            Text(
                                text = "  ▸ HINT ◂  ",
                                style = MaterialTheme.typography.displayMedium.copy(
                                    color = AccentPrimary.copy(alpha = 0.45f),
                                    fontSize = 22.sp,
                                    letterSpacing = 2.sp
                                )
                            )
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                thickness = 0.5.dp,
                                color = AccentPrimary.copy(alpha = 0.2f)
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = currentPuzzle.hint,
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = AccentPrimary.copy(alpha = 0.65f),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (!currentPuzzle.assetRef.isNullOrBlank()) {
                        Spacer(Modifier.height(32.dp))
                        CrypticButton(label = "./download_artefact", onClick = { })
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AccentPrimary.copy(alpha = 0.06f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                            topStart = 6.dp, topEnd = 6.dp
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = AccentPrimary.copy(alpha = 0.25f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                            topStart = 6.dp, topEnd = 6.dp
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                if (terminalOutput.isNotBlank()) {
                    Text(
                        text = terminalOutput,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = AccentPrimary.copy(alpha = 0.85f),
                            lineHeight = 28.sp
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }

                if (isSolved) {
                    puzzle?.unlocksNext?.let { nextId ->
                        CrypticButton(label = "./proceed", onClick = { onNavigateNext(nextId) })
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "root@kryptiko:~# ",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = AccentPrimary.copy(alpha = 0.7f)
                            )
                        )
                        BasicTextField(
                            value = userInput,
                            onValueChange = { userInput = it },
                            textStyle = MaterialTheme.typography.headlineMedium.copy(
                                color = AccentPrimary
                            ),
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

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

fun Modifier.hackerCorners(
    brush: Brush,
    length: Dp = 20.dp,
    strokeWidth: Dp = 2.dp
): Modifier = this.drawWithContent {
    drawContent()

    val lengthPx = length.toPx()
    val strokePx = strokeWidth.toPx()


    drawLine(brush, Offset(0f, 0f), Offset(lengthPx, 0f), strokePx)
    drawLine(brush, Offset(0f, 0f), Offset(0f, lengthPx), strokePx)

    drawLine(brush, Offset(size.width - lengthPx, 0f), Offset(size.width, 0f), strokePx)
    drawLine(brush, Offset(size.width, 0f), Offset(size.width, lengthPx), strokePx)

    drawLine(brush, Offset(0f, size.height), Offset(lengthPx, size.height), strokePx)
    drawLine(brush, Offset(0f, size.height - lengthPx), Offset(0f, size.height), strokePx)

    drawLine(brush, Offset(size.width - lengthPx, size.height), Offset(size.width, size.height), strokePx)
    drawLine(brush, Offset(size.width, size.height - lengthPx), Offset(size.width, size.height), strokePx)
}