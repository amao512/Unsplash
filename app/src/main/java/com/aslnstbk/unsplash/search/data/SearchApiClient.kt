package com.aslnstbk.unsplash.search.data

import com.aslnstbk.unsplash.search.data.models.SearchResultApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiClient {

    @GET("search/photos")
    fun searchImages(@Query("query") query: String): Call<SearchResultApiData>
}