package com.aslnstbk.unsplash.search.data

import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.search.data.models.QueryHistory
import com.aslnstbk.unsplash.search.data.models.SearchResult
import com.aslnstbk.unsplash.search.data.models.SearchResultApiData
import com.aslnstbk.unsplash.search.domain.SearchRepository
import com.aslnstbk.unsplash.utils.mappers.SearchResultApiDataMapper
import retrofit2.Response
import retrofit2.awaitResponse

class DefaultSearchRepository(
    private val appDatabase: AppDatabase,
    private val searchApiClient: SearchApiClient,
    private val searchResultApiDataMapper: SearchResultApiDataMapper
) : SearchRepository {

    override suspend fun getAllSearchHistory(): List<QueryHistory> {
        return appDatabase.searchHistoryDao().getAllSearchHistory()
    }

    override suspend fun checkByQuery(query: String): Boolean {
        val queryHistory: QueryHistory? = appDatabase.searchHistoryDao().getByQuery(query)
        var isExists = false

        if (queryHistory != null){
            isExists = true
        }

        return isExists
    }

    override suspend fun addSearchHistory(queryHistory: QueryHistory) {
        appDatabase.searchHistoryDao().insert(queryHistory)
    }

    override suspend fun deleteSearchHistory(queryHistory: QueryHistory) {
        appDatabase.searchHistoryDao().delete(queryHistory)
    }

    override suspend fun searchImages(
        query: String,
        page: Int,
        result: (SearchResult) -> Unit,
        fail: (String?) -> Unit
    ) {
        try {
            val response: Response<SearchResultApiData> = searchApiClient.searchImages(
                query = query,
                page = page
            ).awaitResponse()

            if (response.isSuccessful){
                val searchResult: SearchResult = searchResultApiDataMapper.map(response.body())
                result(searchResult)
            } else {
                fail(response.message())
            }
        } catch (e: Exception) {
            fail(e.localizedMessage)
        }
    }
}