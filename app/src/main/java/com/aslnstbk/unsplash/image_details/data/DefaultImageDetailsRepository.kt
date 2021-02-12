package com.aslnstbk.unsplash.image_details.data

import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository
import com.aslnstbk.unsplash.utils.mappers.ImageApiDataMapper
import retrofit2.Response
import retrofit2.awaitResponse

class DefaultImageDetailsRepository(
    private val imageDetailsDataSource: ImageDetailsDataSource,
    private val appDatabase: AppDatabase,
    private val imageApiDataMapper: ImageApiDataMapper
) : ImageDetailsRepository {

    override suspend fun getImageById(
        photoId: String,
        result: (Image) -> Unit,
        fail: (String?) -> Unit
    ) {
        try {
            val response: Response<ImageApiData> = imageDetailsDataSource.getImageById(photoId = photoId).awaitResponse()

            if (response.isSuccessful){
                val image: Image = imageApiDataMapper.map(response.body())

                result(image)
            } else {
                fail(response.message())
            }
        } catch (e: Exception) {
            fail(e.localizedMessage)
        }
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