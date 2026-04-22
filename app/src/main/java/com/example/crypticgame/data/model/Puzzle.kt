package com.example.crypticgame.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PuzzleType {
    @SerialName("binary") BINARY,
    @SerialName ("acrostic") ACROSTIC,
    @SerialName ("cipher") CIPHER,
    @SerialName ("steganography")STEGANOGRAPHY,
    @SerialName ("spectrogram") SPECTROGRAM
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

@Serializable
data class Chapter(
    val id: Int,
    val title: String,
    val description: String,
    val puzzles: List<Puzzle>
)