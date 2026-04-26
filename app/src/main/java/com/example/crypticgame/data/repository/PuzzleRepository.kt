package com.example.crypticgame.data.repository

import com.example.crypticgame.data.model.Puzzle
import com.example.crypticgame.data.model.PuzzleType

class PuzzleRepository{
    fun getPuzzleById (id : Int): Puzzle?{
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
        )
        return puzzles.find { it.id == id }
    }
}