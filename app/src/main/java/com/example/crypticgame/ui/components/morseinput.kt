package com.example.crypticgame.ui.components

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.example.crypticgame.ui.theme.AccentPrimary
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val morse_to_char = mapOf(
    ".-" to 'A', "-..." to 'B', "-.-." to 'C', "-.." to 'D',
    "." to 'E', "..-." to 'F', "--." to 'G', "...." to 'H',
    ".." to 'I', ".---" to 'J', "-.-" to 'K', ".-.." to 'L',
    "--" to 'M', "-." to 'N', "---" to 'O', ".--." to 'P',
    "--.-" to 'Q', ".-." to 'R', "..." to 'S', "-" to 'T',
    "..-" to 'U', "...-" to 'V', ".--" to 'W', "-..-" to 'X',
    "-.--" to 'Y', "--.." to 'Z',
    "-----" to '0', ".----" to '1', "..---" to '2', "...--" to '3',
    "....-" to '4', "....." to '5', "-...." to '6', "--..." to '7',
    "---.." to '8', "----." to '9'
)

private const val dash_threshold = 300L
private const val letter_gap = 1500L

@Composable
fun MorseInputPad(
    currentWord: String,
    onWordChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentSymbols by remember { mutableStateOf("") }
    var keyPressed by remember { mutableStateOf(false) }
    var pressStartTime by remember { mutableStateOf(0L) }
    var letterCommitJob by remember { mutableStateOf<Job?>(null) }

    val currentWordUpdated by rememberUpdatedState(currentWord)
    val onWordChangeUpdated by rememberUpdatedState(onWordChange)
    val onSubmitUpdated by rememberUpdatedState(onSubmit)

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(durationMs: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vm.defaultVibrator.vibrate(
                VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            @Suppress("DEPRECATION")
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            @Suppress("DEPRECATION")
            v.vibrate(durationMs)
        }
    }

    fun commitLetter() {
        if (currentSymbols.isNotEmpty()) {
            val letter = morse_to_char[currentSymbols]
            val newWord = if (letter != null) currentWordUpdated + letter else currentWordUpdated + "?"
            onWordChangeUpdated(newWord)
            currentSymbols = ""
        }
    }

    fun scheduleLetterCommit() {
        letterCommitJob?.cancel()
        letterCommitJob = scope.launch {
            delay(letter_gap)
            commitLetter()
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            contentAlignment = Alignment.Center
        ) {
            if (currentSymbols.isNotEmpty()) {
                Text(
                    text = "[$currentSymbols]",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = AccentPrimary.copy(alpha = 0.5f),
                        fontSize = 20.sp
                    )
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(
                    if (keyPressed) AccentPrimary.copy(alpha = 0.18f)
                    else AccentPrimary.copy(alpha = 0.07f)
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            keyPressed = true
                            pressStartTime = System.currentTimeMillis()
                            letterCommitJob?.cancel()

                            tryAwaitRelease()

                            keyPressed = false

                            val held = System.currentTimeMillis() - pressStartTime
                            if (held >= dash_threshold) {
                                currentSymbols += "-"
                                vibrate(120)
                            } else {
                                currentSymbols += "."
                                vibrate(40)
                            }

                            scheduleLetterCommit()
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (keyPressed) "●" else "○",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = if (keyPressed) AccentPrimary else AccentPrimary.copy(alpha = 0.6f),
                    fontSize = 48.sp
                )
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "tap · hold ",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = AccentPrimary.copy(alpha = 0.3f),
                fontSize = 11.sp,
                letterSpacing = 2.sp
            )
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ControlKey(
                label = "SPACE",
                subLabel = "word gap",
                modifier = Modifier.weight(1f),
                onClick = {
                    letterCommitJob?.cancel()
                    commitLetter()
                    onWordChangeUpdated(currentWordUpdated + " ")
                    vibrate(30)
                }
            )

            ControlKey(
                label = "DELETE",
                subLabel = "clear last",
                modifier = Modifier.weight(1f),
                onClick = {
                    letterCommitJob?.cancel()
                    if (currentSymbols.isNotEmpty()) {
                        currentSymbols = currentSymbols.dropLast(1)
                    } else if (currentWordUpdated.isNotEmpty()) {
                        onWordChangeUpdated(currentWordUpdated.dropLast(1))
                    }
                    vibrate(20)
                }
            )

            ControlKey(
                label = "EXECUTE",
                subLabel = "submit",
                modifier = Modifier.weight(1f),
                onClick = {
                    letterCommitJob?.cancel()
                    commitLetter()
                    val final = currentWordUpdated.trim()
                    if (final.isNotBlank()) {
                        onSubmitUpdated(final)
                        onWordChangeUpdated("")
                        currentSymbols = ""
                        vibrate(60)
                    }
                }
            )
        }
    }
}

@Composable
private fun ControlKey(
    label: String,
    subLabel: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val currentOnClick by rememberUpdatedState(onClick)

    Box(
        modifier = modifier
            .height(50.dp)
            .border(1.dp, AccentPrimary.copy(alpha = 0.4f))
            .background(AccentPrimary.copy(alpha = 0.08f))
            .clickable { currentOnClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = AccentPrimary,
                    fontSize = 14.sp,
                    letterSpacing = 1.5.sp
                )
            )
            Text(
                text = subLabel,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = AccentPrimary.copy(alpha = 0.5f),
                    fontSize = 9.sp,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}