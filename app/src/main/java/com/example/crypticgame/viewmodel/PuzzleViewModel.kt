package com.example.crypticgame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypticgame.data.db.ProgressDao
import com.example.crypticgame.data.model.Progress
import com.example.crypticgame.data.model.Puzzle
import com.example.crypticgame.data.repository.PuzzleRepository
import com.example.crypticgame.utils.CryptoUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

class PuzzleViewModel(
    private val puzzleId:Int,
    private val repository: PuzzleRepository = PuzzleRepository(),
    private val progressDao : ProgressDao
) : ViewModel(){
    private val _puzzle = MutableStateFlow<Puzzle?>(null)
    val puzzle = _puzzle.asStateFlow()

    private val _isSolved = MutableStateFlow(false)
    val isSolved = _isSolved.asStateFlow()

    private val _terminalOutput = MutableStateFlow("> AWAITING INPUT...")
    val terminalOutput = _terminalOutput.asStateFlow()

    init {
        _puzzle.value = repository.getPuzzleById(puzzleId)
    }

    fun submitAnswer(input: String) {
        val currentPuzzle = _puzzle.value ?: return
        val cleanInput = input.lowercase().trim()
        val inputHash = hashString(cleanInput)

        if (inputHash == currentPuzzle.answerHash) {
            _terminalOutput.value = currentPuzzle.storyFragment

            _isSolved.value = true

            viewModelScope.launch {
                try {
                    progressDao.upsertProgress(
                        Progress(puzzleId = currentPuzzle.id, chapterId = 1, isSolved = true)
                    )
                } catch (e: Exception) {
                    _terminalOutput.value = "> FATAL ERROR: ${e.localizedMessage}"
                }
            }
        } else {
            _terminalOutput.value = "> ACCESS DENIED. INVALID CREDENTIALS."
        }
    }

    private fun hashString(input: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray())
            .joinToString("") { "%02x".format(it) }
    }

}


