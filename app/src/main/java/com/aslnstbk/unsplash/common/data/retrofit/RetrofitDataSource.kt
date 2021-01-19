package com.aslnstbk.unsplash.common.data.retrofit

import com.aslnstbk.unsplash.common.data.models.api.PhotoApiData
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitDataSource {

    @GET("photos")
    fun getPhotos(): Call<List<PhotoApiData>>
}