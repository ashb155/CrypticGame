package com.example.crypticgame.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crypticgame.ui.theme.*
import kotlinx.coroutines.delay

fun Modifier.glowBorder(
    color: Color,
    borderWidth: Dp = 1.dp,
    glowRadius: Dp = 6.dp
): Modifier = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            asFrameworkPaint().apply {
                isAntiAlias = true
                style = android.graphics.Paint.Style.STROKE
                strokeWidth = borderWidth.toPx()
                this.color = android.graphics.Color.TRANSPARENT
                setShadowLayer(glowRadius.toPx(), 0f, 0f, color.toArgb())
            }
        }
        canvas.drawRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height,
            paint = paint
        )
    }
}

@Composable
fun CrypticButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var pressed by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "btn_pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "btn_glow"
    )

    val bgColor by animateColorAsState(
        targetValue = if (pressed && enabled) AccentPrimary else Color.Transparent,
        animationSpec = tween(150),
        label = "btn_bg"
    )

    val textColor by animateColorAsState(
        targetValue = if (pressed && enabled) BackgroundDark else if (enabled) AccentPrimary else TextDim,
        animationSpec = tween(150),
        label = "btn_text"
    )

    val borderColor = if (enabled) AccentPrimary.copy(alpha = glowAlpha) else LockedGray

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(150)
            pressed = false
        }
    }

    Box(
        modifier = modifier
            .width(220.dp)
            .then(
                if (enabled) Modifier.glowBorder(AccentPrimary.copy(alpha = glowAlpha * 0.9f), glowRadius = 8.dp)
                else Modifier
            )
            .border(3.dp, borderColor)
            .background(bgColor)
            .clickable(enabled = enabled) {
                pressed = true
                onClick()
            }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                color = textColor,
                letterSpacing = 4.sp,
                fontFamily = ShareTechMono
            )
        )
    }
}