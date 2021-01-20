package com.aslnstbk.unsplash.history.domain

import com.aslnstbk.unsplash.history.data.models.History

interface HistoryRepository {

    suspend fun getAllHistory(): List<History>

    suspend fun addHistory(history: History)

    suspend fun deleteHistory(imageId: String)
}