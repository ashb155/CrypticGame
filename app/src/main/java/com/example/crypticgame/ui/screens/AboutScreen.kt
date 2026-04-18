package com.example.crypticgame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.ui.components.TypeText
import com.example.crypticgame.ui.theme.AccentPrimary
import com.example.crypticgame.ui.theme.BackgroundDark
import com.example.crypticgame.ui.theme.TextSecondary

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TypeText(
                fullText = "ABOUT",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 48.sp,
                    color = AccentPrimary,
                    letterSpacing = 12.sp,
                ),
                delayPerChar = 40L

            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "welcome to about",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }

    }
}
