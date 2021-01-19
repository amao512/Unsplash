package com.aslnstbk.unsplash.image_details.data

import com.aslnstbk.unsplash.common.models.Photo
import com.aslnstbk.unsplash.common.models.api.PhotoApiData
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository
import com.aslnstbk.unsplash.utils.mappers.PhotoApiDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultImageDetailsRepository(
    private val imageDetailsDataSource: ImageDetailsDataSource,
    private val photoApiDataMapper: PhotoApiDataMapper
) : ImageDetailsRepository {

    override fun getPhotoById(
        photoId: String,
        result: (Photo) -> Unit,
        fail: (String?) -> Unit
    ) {
        imageDetailsDataSource.getPhotoById(photoId = photoId)
            .enqueue(object : Callback<PhotoApiData> {
                override fun onResponse(
                    call: Call<PhotoApiData>,
                    response: Response<PhotoApiData>
                ) {
                    if (response.isSuccessful){
                        val photo: Photo = photoApiDataMapper.map(response.body())

                        result(photo)
                    } else {
                        fail(response.message())
                    }
                }

                override fun onFailure(call: Call<PhotoApiData>, t: Throwable) {
                    fail(t.localizedMessage)
                }
            })
    }
}