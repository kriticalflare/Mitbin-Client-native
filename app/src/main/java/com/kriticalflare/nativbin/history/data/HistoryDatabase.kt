package com.kriticalflare.nativbin.history.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ViewedPaste::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}