package com.aslnstbk.unsplash.history.data

import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.history.data.models.History
import com.aslnstbk.unsplash.history.domain.HistoryRepository

class DefaultHistoryRepository(
    private val appDatabase: AppDatabase
) : HistoryRepository {

    override suspend fun getAllHistory(): List<History> {
        return appDatabase.historyDao().getAllHistory()
    }

    override suspend fun addHistory(history: History) {
        appDatabase.historyDao().insert(history)
    }

    override suspend fun deleteHistory(imageId: String) {
        appDatabase.historyDao().delete(imageId)
    }
}