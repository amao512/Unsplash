package com.aslnstbk.unsplash.home.data

import com.aslnstbk.unsplash.common.data.model.RetrofitDataSource
import com.aslnstbk.unsplash.common.models.Photo
import com.aslnstbk.unsplash.common.models.api.PhotoApiData
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.utils.mappers.PhotoApiDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultHomeRepository(
    private val apiClient: RetrofitDataSource,
    private val photoApiDataMapper: PhotoApiDataMapper
) : HomeRepository {

    override fun getPhotos(
        result: (List<Photo>) -> Unit,
        fail: (String?) -> Unit
    ) {
        apiClient.getPhotos().enqueue(object : Callback<List<PhotoApiData>> {
            override fun onResponse(
                call: Call<List<PhotoApiData>>,
                response: Response<List<PhotoApiData>>
            ) {
                if (response.isSuccessful) {
                    val photoList: List<Photo> = photoApiDataMapper.map(response.body())
                    result(photoList)
                } else {
                    fail(response.message())
                }
            }

            override fun onFailure(
                call: Call<List<PhotoApiData>>,
                t: Throwable
            ) {
                fail(t.localizedMessage)
            }
        })
    }
}