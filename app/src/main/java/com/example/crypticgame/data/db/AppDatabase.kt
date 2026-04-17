package com.example.crypticgame.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crypticgame.data.model.Progress

@Database(entities = [Progress::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
}
