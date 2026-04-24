package com.example.crypticgame.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PuzzleType {
    @SerialName("binary") BINARY,
    @SerialName("acrostic") ACROSTIC,
    @SerialName("cipher") CIPHER,
    @SerialName("steganography") STEGANOGRAPHY,
    @SerialName("spectrogram") SPECTROGRAM,
    @SerialName("vigenere") VIGENERE,
    @SerialName("morse") MORSE,
    @SerialName("hex") HEX,
    @SerialName("base64") BASE64,
    @SerialName("exif") EXIF,
    @SerialName("osint") OSINT
}

@Serializable
data class Puzzle(
    val id: Int,
    val type: PuzzleType,
    val title: String,
    val content: String,
    val hint: String,
    val answerHash: String,
    val storyFragment: String,
    val unlocksNext: Int? = null,
    val assetRef: String? = null
)

