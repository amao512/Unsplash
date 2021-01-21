package com.aslnstbk.unsplash.home.data

import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import retrofit2.Call
import retrofit2.http.GET

interface HomeApiClient {

    @GET("photos")
    fun getImages(): Call<List<ImageApiData>>
}