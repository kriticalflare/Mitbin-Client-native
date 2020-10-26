package com.kriticalflare.nativbin.history.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(val dao: HistoryDao) {
    suspend fun insertViewedPaste(viewedPaste: ViewedPaste) {
        dao.insert(viewedPaste)
    }

    fun getViewedPastes(): Flow<List<ViewedPaste>> {
        return dao.getHistory()
    }
}