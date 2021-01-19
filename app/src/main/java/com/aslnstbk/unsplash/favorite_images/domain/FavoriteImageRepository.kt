package com.aslnstbk.unsplash.favorite_images.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage

interface FavoriteImageRepository {

    @WorkerThread
    suspend fun getAllFavoriteImages(): LiveData<List<FavoriteImage>>

    @WorkerThread
    suspend fun addFavoriteImage(favoriteImage: FavoriteImage)

    @WorkerThread
    suspend fun updateFavoriteImage(favoriteImage: FavoriteImage)

    @WorkerThread
    suspend fun delete(favoriteImage: FavoriteImage)
}