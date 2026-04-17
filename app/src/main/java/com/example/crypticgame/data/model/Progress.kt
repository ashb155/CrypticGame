package com.example.crypticgame.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey
    val puzzleId: Int,
    val chapterId: Int,
    val isSolved: Boolean = false,
    val solvedAt: Long? = null
)