package com.example.crypticgame.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay
import kotlin.random.Random

private fun glitchText(text: String, intensity: Float): String {
    val glitchChars = "!@#\$%^&*()_+-=[]{}|;':,./<>?"
    return text.map {
        if (Random.nextFloat() < intensity) glitchChars.random() else it
    }.joinToString("")
}


@Composable
fun TypeText(
    fullText: String,
    modifier: Modifier = Modifier,
    delayPerChar: Long = 40L,
    style: TextStyle = TextStyle.Default,
    enableGlitch: Boolean = true,
    neonColor: Color = Color(0xFF00FF41),
    glitchIntensity: Float = 0.15f
) {
    var displayedText by remember { mutableStateOf("") }
    var isTextGlitching by remember { mutableStateOf(false) }
    var glitchOffsetX by remember { mutableFloatStateOf(0f) }
    var glitchAlpha by remember { mutableFloatStateOf(1f) }
    var flickerAlpha by remember { mutableFloatStateOf(1f) }

    val infiniteTransition = rememberInfiniteTransition(label = "glow_transition")

    val glow by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 22f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_anim"
    )


    LaunchedEffect(fullText) {
        val builder = StringBuilder()
        fullText.forEach { char ->
            delay(delayPerChar)
            builder.append(char)
            displayedText = builder.toString()
        }
    }

    LaunchedEffect(enableGlitch, fullText) {
        if (!enableGlitch) return@LaunchedEffect

        while (true) {
            delay(Random.nextLong(500L, 1500L))

            isTextGlitching = true
            repeat(Random.nextInt(4, 8)) {
                glitchOffsetX = Random.nextInt(-10, 10).toFloat()
                glitchAlpha = Random.nextFloat() * 0.5f + 0.5f
                delay(Random.nextLong(30L, 80L))
            }
            isTextGlitching = false
            glitchOffsetX = 0f
            glitchAlpha = 1f
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(400, 1200))
            flickerAlpha = Random.nextFloat() * 0.4f + 0.5f
            delay(50)
            flickerAlpha = 1f
        }
    }

    val textToRender = if (enableGlitch && isTextGlitching) {
        glitchText(displayedText, glitchIntensity)
    } else {
        displayedText
    }

    Box(modifier = modifier) {
        Text(
            text = textToRender,
            modifier = Modifier.graphicsLayer {
                translationX = glitchOffsetX
                alpha = glitchAlpha * flickerAlpha
            },
            style = style.copy(color = Color.Red)
        )

        Text(
            text = textToRender,
            modifier = Modifier.graphicsLayer {
                translationX = -glitchOffsetX * 0.5f
                alpha = glitchAlpha * flickerAlpha
            },
            style = style.copy(color = Color.Blue)
        )

        Text(
            text = textToRender,
            modifier = Modifier.graphicsLayer {
                alpha = flickerAlpha
            },
            style = style.copy(color = Color.White)
        )

        Text(
            text = textToRender,
            modifier = Modifier.graphicsLayer {
                alpha = flickerAlpha * 0.9f
            },
            style = style.copy(
                color = neonColor,
                shadow = Shadow(
                    color = neonColor,
                    blurRadius = glow * 0.5f
                )
            )
        )

        Text(
            text = textToRender,
            modifier = Modifier.graphicsLayer {
                alpha = flickerAlpha * 0.6f
            },
            style = style.copy(
                color = neonColor,
                shadow = Shadow(
                    color = neonColor.copy(alpha = 0.7f),
                    blurRadius = glow * 1.5f
                )
            )
        )
    }
}