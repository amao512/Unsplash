package com.aslnstbk.unsplash.search.domain

import androidx.annotation.WorkerThread
import com.aslnstbk.unsplash.search.data.models.SearchResult
import com.aslnstbk.unsplash.search.data.models.QueryHistory

interface SearchRepository {

    @WorkerThread
    suspend fun getAllSearchHistory(): List<QueryHistory>

    @WorkerThread
    suspend fun checkByQuery(query: String): Boolean

    @WorkerThread
    suspend fun addSearchHistory(searchHistory: QueryHistory)

    @WorkerThread
    suspend fun deleteSearchHistory(queryHistory: QueryHistory)

    @WorkerThread
    fun searchImages(
        query: String,
        page: Int,
        result: (SearchResult) -> Unit,
        fail: (String?) -> Unit
    )
}