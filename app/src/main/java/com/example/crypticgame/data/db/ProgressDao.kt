package com.example.crypticgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crypticgame.data.model.Progress
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress WHERE chapterId = :chapterId")
    fun getProgressForChapter(chapterId: Int): Flow<List<Progress>>

    @Query("SELECT * FROM progress WHERE puzzleId = :puzzleId")
    suspend fun getProgressForPuzzle(puzzleId: Int): Progress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: Progress)

    @Query("SELECT COUNT(*) FROM progress WHERE chapterId = :chapterId AND isSolved = 1")
    suspend fun solvedCountForChapter(chapterId: Int): Int
}