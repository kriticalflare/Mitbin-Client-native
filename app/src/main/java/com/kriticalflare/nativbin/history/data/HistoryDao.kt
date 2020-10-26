package com.kriticalflare.nativbin.history.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM viewedpaste")
    fun getHistory(): Flow<List<ViewedPaste>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(viewedPaste: ViewedPaste)

    @Query("DELETE FROM viewedpaste")
    suspend fun delete()
}