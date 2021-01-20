package com.aslnstbk.unsplash.image_details.data

import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageDetailsDataSource {

    @GET("photos/{id}")
    fun getPhotoById(@Path("id") photoId: String): Call<ImageApiData>
}