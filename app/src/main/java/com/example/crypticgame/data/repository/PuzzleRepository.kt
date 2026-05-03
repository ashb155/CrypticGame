package com.example.crypticgame.data.repository

import com.example.crypticgame.data.model.Puzzle
import com.example.crypticgame.data.model.PuzzleType

class PuzzleRepository{
    val puzzles = listOf(
        Puzzle(
            id = 1,
            type = PuzzleType.BINARY,
            title = "KEY 1",
            content = "01101000 01100101 01101100 01101100 01101111",
            hint = " two states. eight positions. one twenty eight possibilities. one truth. ",
            answerHash = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
            storyFragment = "INITIALIZING",
            unlocksNext = 2
        ),

        Puzzle(
            id = 2,
            type = PuzzleType.MORSE,
            title = "KEY 2",
            content = "3S·2S·2L1S·1L1S\n 1S1L·1S2L2S",
            hint = "short or long.",
            answerHash = "d041924c15885af6d06530a425c6dbffc80520150c4dd264f40b4364b12421a8",
            storyFragment = "still transmitting.",
            unlocksNext = 3
        )
    )
    fun getPuzzleById (id : Int): Puzzle?{
        return puzzles.find { it.id == id }
    }
}