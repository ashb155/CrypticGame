package com.example.crypticgame.ui.screens


import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.ui.components.CrypticButton
import com.example.crypticgame.ui.theme.*
import com.example.crypticgame.ui.components.TypeText
import kotlinx.coroutines.delay

@Composable
fun MainMenuScreen(
    hasProgress: Boolean = true,
    onStart: () -> Unit,
    onAbout: () -> Unit
) {
    var startMenuAnimation by remember { mutableStateOf(false) }
    var titleVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        titleVisible = true
        delay(2100)
        startMenuAnimation = true
    }

    val titleAlpha by animateFloatAsState(
        targetValue = if (titleVisible) 1f else 0f,
        animationSpec = tween(500, easing = EaseInOut)
    )

    val progress by animateFloatAsState(
        targetValue = if (startMenuAnimation) 1f else 0f,
        animationSpec = tween(800, easing = EaseInOut)
    )

    val titleOffset = (-100 * progress).dp
    val menuOffset = (40 * (1 - progress)).dp
    val menuAlpha = progress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        { Box(
            modifier = Modifier
                .offset(y = titleOffset)
                .height(80.dp),
            contentAlignment = Alignment.Center
        ){
            TypeText(
                fullText = "KRYPTIKO",
                modifier = Modifier,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 56.sp,
                    color = AccentPrimary,
                    letterSpacing = 16.sp
                )
            )
        }}

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = menuOffset)
                .alpha(menuAlpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                contentAlignment = Alignment.Center
            ) {
                TypeText(
                    fullText = "dekrypt - discover - prevail",
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleMedium,
                    glitchIntensity = 0.1f
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            CrypticButton(
                label = if (hasProgress) "./resume" else "./ play",
                onClick = onStart
            )
            Spacer(modifier = Modifier.height(12.dp))
            CrypticButton(label = "./info", onClick = onAbout)
        }
    }
}

