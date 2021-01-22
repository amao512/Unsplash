package com.aslnstbk.unsplash.search.domain

import androidx.annotation.WorkerThread
import com.aslnstbk.unsplash.search.data.models.SearchResult
import com.aslnstbk.unsplash.search.data.models.SearchHistory

interface SearchRepository {

    @WorkerThread
    suspend fun getAllSearchHistory(): List<SearchHistory>

    @WorkerThread
    suspend fun checkByQuery(query: String): Boolean

    @WorkerThread
    suspend fun addSearchHistory(searchHistory: SearchHistory)

    @WorkerThread
    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    @WorkerThread
    fun searchImages(
        query: String,
        result: (SearchResult) -> Unit,
        fail: (String?) -> Unit
    )
}