package com.aslnstbk.unsplash.common.data.retrofit

import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitDataSource {

    @GET("photos")
    fun getImages(): Call<List<ImageApiData>>
}