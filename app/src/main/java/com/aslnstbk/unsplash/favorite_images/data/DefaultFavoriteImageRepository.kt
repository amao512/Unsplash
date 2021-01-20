package com.aslnstbk.unsplash.favorite_images.data

import androidx.lifecycle.LiveData
import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.favorite_images.domain.FavoriteImageRepository

class DefaultFavoriteImageRepository(
    private val appDatabase: AppDatabase
) : FavoriteImageRepository {

    override suspend fun getAllFavoriteImages(): LiveData<List<FavoriteImage>> {
        return appDatabase.favoriteImageDao().getAllFavoriteImage()
    }

    override suspend fun addFavoriteImage(favoriteImage: FavoriteImage) {
        appDatabase.favoriteImageDao().insert(favoriteImage)
    }

    override suspend fun updateFavoriteImage(favoriteImage: FavoriteImage) {
        appDatabase.favoriteImageDao().update(favoriteImage)
    }

    override suspend fun delete(imageId: String) {
        appDatabase.favoriteImageDao().delete(imageId)
    }
}