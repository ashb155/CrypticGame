package com.example.crypticgame.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.ui.components.CrypticButton
import com.example.crypticgame.ui.components.TerminalKeyboard
import com.example.crypticgame.ui.components.TypeText
import com.example.crypticgame.ui.theme.AccentPrimary
import com.example.crypticgame.ui.theme.BackgroundDark
import com.example.crypticgame.ui.theme.ScanlineGreen
import com.example.crypticgame.viewmodel.PuzzleViewModel
import kotlinx.coroutines.delay

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
    var isHintRevealed by remember { mutableStateOf(false) }

    var cursorVisible by remember { mutableStateOf(true) }
    LaunchedEffect(userInput) {
        cursorVisible = true
        while (true) {
            delay(530)
            cursorVisible = !cursorVisible
        }
    }

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
                .systemBarsPadding()
                .padding(horizontal = 20.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .weight(1f)
                    .padding(bottom = 16.dp),
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isHintRevealed = !isHintRevealed }
                                .padding(vertical = 12.dp)
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp, color = AccentPrimary.copy(alpha = 0.2f))

                            Box(
                                modifier = Modifier.width(140.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                TypeText(
                                    fullText = if (!isHintRevealed) "  ▸ HINT ◂  " else "  ▸ BACK ◂  ",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = AccentPrimary.copy(alpha = 0.6f),
                                        letterSpacing = 2.sp
                                    )
                                )
                            }

                            HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp, color = AccentPrimary.copy(alpha = 0.2f))
                        }
                    }

                    if (!currentPuzzle.assetRef.isNullOrBlank()) {
                        Spacer(Modifier.height(32.dp))
                        CrypticButton(label = "./download_artefact", onClick = { })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AccentPrimary.copy(alpha = 0.06f),
                        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = AccentPrimary.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .padding(bottom = 16.dp)
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
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "root@kryptiko:~# ",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = AccentPrimary.copy(alpha = 0.7f),
                                    fontSize = 18.sp
                                )
                            )
                            Text(
                                text = userInput,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = AccentPrimary
                                ),
                                maxLines = 1
                            )
                            Text(
                                text = "█",
                                modifier = Modifier.alpha(if (cursorVisible) 1f else 0f),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = AccentPrimary
                                ),
                                maxLines = 1
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            AnimatedContent(
                                targetState = isHintRevealed,
                                transitionSpec = {
                                    val duration = 600

                                    if (targetState) {
                                        (slideInVertically(animationSpec = tween(duration, easing = FastOutSlowInEasing)) { height -> height / 3 } +
                                                fadeIn(animationSpec = tween(duration)))
                                            .togetherWith(
                                                slideOutVertically(animationSpec = tween(duration, easing = FastOutSlowInEasing)) { height -> -height / 3 } +
                                                        fadeOut(animationSpec = tween(duration))
                                            )
                                    } else {
                                        (slideInVertically(animationSpec = tween(duration, easing = FastOutSlowInEasing)) { height -> -height / 3 } +
                                                fadeIn(animationSpec = tween(duration)))
                                            .togetherWith(
                                                slideOutVertically(animationSpec = tween(duration, easing = FastOutSlowInEasing)) { height -> height / 3 } +
                                                        fadeOut(animationSpec = tween(duration))
                                            )
                                    }
                                },
                                label = "Terminal Buffer Swap"
                            ) { showHint ->

                                if (showHint) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        Spacer(Modifier.height(16.dp))

                                        puzzle?.let { currentPuzzle ->
                                            Text(
                                                text = currentPuzzle.hint.replace(".",".\n"),
                                                style = MaterialTheme.typography.headlineMedium.copy(
                                                    color = AccentPrimary.copy(alpha = 0.8f),
                                                    fontSize = 20.sp,
                                                    textAlign = TextAlign.Center,
                                                    lineHeight = 28.sp
                                                ),
                                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                            )
                                        }

                                    }
                                } else {
                                    TerminalKeyboard(
                                        onKeyPress = { char ->
                                            if (userInput.length < 30) {
                                                userInput += char
                                            }
                                        },
                                        onBackspace = {
                                            if (userInput.isNotEmpty()) {
                                                userInput = userInput.dropLast(1)
                                            }
                                        },
                                        onSubmit = {
                                            if (userInput.isNotBlank()) {
                                                viewModel.submitAnswer(userInput)
                                                userInput = ""
                                            }
                                        }
                                    )
                                }
                            }
                        }

                    }
                }
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
    val dimBrush = SolidColor(AccentPrimary.copy(alpha = 0.4f))
    val dashEffect = PathEffect.dashPathEffect(floatArrayOf(2f, 18f), 0f)

    drawLine(brush, Offset(0f, 0f), Offset(lengthPx, 0f), strokePx)
    drawLine(brush, Offset(0f, 0f), Offset(0f, lengthPx), strokePx)

    drawLine(brush, Offset(size.width - lengthPx, 0f), Offset(size.width, 0f), strokePx)
    drawLine(brush, Offset(size.width, 0f), Offset(size.width, lengthPx), strokePx)

    drawLine(brush, Offset(0f, size.height), Offset(lengthPx, size.height), strokePx)
    drawLine(brush, Offset(0f, size.height - lengthPx), Offset(0f, size.height), strokePx)

    drawLine(brush, Offset(size.width - lengthPx, size.height), Offset(size.width, size.height), strokePx)
    drawLine(brush, Offset(size.width, size.height - lengthPx), Offset(size.width, size.height), strokePx)

    drawLine(dimBrush, Offset(lengthPx, 0f), Offset(size.width - lengthPx, 0f), strokePx, pathEffect = dashEffect)
    drawLine(dimBrush, Offset(lengthPx, size.height), Offset(size.width - lengthPx, size.height), strokePx, pathEffect = dashEffect)
    drawLine(dimBrush, Offset(0f, lengthPx), Offset(0f, size.height - lengthPx), strokePx, pathEffect = dashEffect)
    drawLine(dimBrush, Offset(size.width, lengthPx), Offset(size.width, size.height - lengthPx), strokePx, pathEffect = dashEffect)
}