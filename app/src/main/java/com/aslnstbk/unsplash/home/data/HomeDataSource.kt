package com.aslnstbk.unsplash.home.data

import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.aslnstbk.unsplash.home.data.models.SearchResultApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeDataSource {

    @GET("photos")
    fun getImages(): Call<List<ImageApiData>>

    @GET("search/photos")
    fun searchImages(@Query("query") query: String): Call<SearchResultApiData>
}