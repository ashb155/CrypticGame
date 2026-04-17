package com.example.crypticgame.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CrypticColorScheme = darkColorScheme(
    background = BackgroundDark,
    surface = SurfaceDark,
    primary = AccentPrimary,
    onPrimary = BackgroundDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    secondary = AccentDim,
    onSecondary = TextPrimary,
    error = WrongRed,
)

@Composable
fun CrypticGameTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CrypticColorScheme,
        typography = CrypticTypography,
        content = content
    )
}