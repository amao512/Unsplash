package com.aslnstbk.unsplash.image_details.data

import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository
import com.aslnstbk.unsplash.utils.mappers.ImageApiDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultImageDetailsRepository(
    private val imageDetailsDataSource: ImageDetailsDataSource,
    private val appDatabase: AppDatabase,
    private val imageApiDataMapper: ImageApiDataMapper
) : ImageDetailsRepository {

    override fun getImageById(
        photoId: String,
        result: (Image) -> Unit,
        fail: (String?) -> Unit
    ) {
        imageDetailsDataSource.getImageById(photoId = photoId)
            .enqueue(object : Callback<ImageApiData> {
                override fun onResponse(
                    call: Call<ImageApiData>,
                    response: Response<ImageApiData>
                ) {
                    if (response.isSuccessful){
                        val image: Image = imageApiDataMapper.map(response.body())

                        result(image)
                    } else {
                        fail(response.message())
                    }
                }

                override fun onFailure(call: Call<ImageApiData>, t: Throwable) {
                    fail(t.localizedMessage)
                }
            })
    }

    override suspend fun addFavoriteImage(favoriteImage: FavoriteImage) {
        appDatabase.favoriteImageDao().insert(favoriteImage)
    }

    override suspend fun removeFavoriteImage(imageId: String) {
        appDatabase.favoriteImageDao().delete(imageId)
    }

    override suspend fun checkById(imageId: String): Boolean {
        val favoriteImage: FavoriteImage? = appDatabase.favoriteImageDao().getById(imageId)
        var isFavorite = false

        if(favoriteImage != null){
            isFavorite = true
        }

        return isFavorite
    }
}