package com.example.crypticgame.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.crypticgame.R


val VT323 = FontFamily(Font(R.font.vt323))
val ShareTechMono = FontFamily(Font(R.font.sharetechmono))
val SpaceGrotesk = FontFamily(Font(R.font.spacegrotesk))

val CrypticTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = VT323,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        letterSpacing = 6.sp,
        color = TextPrimary
    ),
    displayMedium = TextStyle(
        fontFamily = VT323,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        letterSpacing = 4.sp,
        color = AccentPrimary
    ),

    headlineMedium = TextStyle(
        fontFamily = ShareTechMono,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 2.sp,
        color = TextPrimary
    ),
    bodyLarge = TextStyle(
        fontFamily = ShareTechMono,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 1.sp,
        color = TextSecondary
    ),
    bodyMedium = TextStyle(
        fontFamily = ShareTechMono,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 1.sp,
        color = TextSecondary
    ),

    titleMedium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.5.sp,
        color = TextPrimary
    ),
    labelLarge = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 1.sp,
        color = AccentPrimary
    ),
    labelSmall = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 1.5.sp,
        color = TextDim
    )
)