package com.aslnstbk.unsplash.search.data

import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.search.data.models.SearchResult
import com.aslnstbk.unsplash.search.data.models.SearchResultApiData
import com.aslnstbk.unsplash.search.data.models.SearchHistory
import com.aslnstbk.unsplash.search.domain.SearchRepository
import com.aslnstbk.unsplash.utils.mappers.SearchResultApiDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultSearchRepository(
    private val appDatabase: AppDatabase,
    private val searchApiClient: SearchApiClient,
    private val searchResultApiDataMapper: SearchResultApiDataMapper
) : SearchRepository {

    override suspend fun getAllSearchHistory(): List<SearchHistory> {
        return appDatabase.searchHistoryDao().getAllSearchHistory()
    }

    override suspend fun checkByQuery(query: String): Boolean {
        val searchHistory: SearchHistory? = appDatabase.searchHistoryDao().getByQuery(query)
        var isExists = false

        if (searchHistory != null){
            isExists = true
        }

        return isExists
    }

    override suspend fun addSearchHistory(searchHistory: SearchHistory) {
        appDatabase.searchHistoryDao().insert(searchHistory)
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        appDatabase.searchHistoryDao().delete(searchHistory)
    }

    override fun searchImages(
        query: String,
        page: Int,
        result: (SearchResult) -> Unit,
        fail: (String?) -> Unit
    ) {
        searchApiClient.searchImages(
            query = query,
            page = page
        ).enqueue(object : Callback<SearchResultApiData> {
            override fun onResponse(
                call: Call<SearchResultApiData>,
                response: Response<SearchResultApiData>
            ) {
                if (response.isSuccessful){
                    val searchResult: SearchResult = searchResultApiDataMapper.map(response.body())

                    result(searchResult)
                } else {
                    fail(response.message())
                }
            }

            override fun onFailure(call: Call<SearchResultApiData>, t: Throwable) {
                fail(t.localizedMessage)
            }
        })
    }
}