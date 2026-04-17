package com.example.crypticgame.data.model

import kotlinx.serialization.Serializable

enum class PuzzleType {
    BINARY,
    ACROSTIC,
    CIPHER,
    STEGANOGRAPHY,
    SPECTROGRAM
}

@Serializable
data class Puzzle(
    val id: Int,
    val type: String,
    val title: String,
    val content: String,
    val hint: String,
    val answerHash: String,
    val unlocksWidth: String? = null,
    val assetRef: String? = null
)

@Serializable
data class Chapter(
    val id: Int,
    val title: String,
    val description: String,
    val puzzles: List<Puzzle>
)