package com.aslnstbk.unsplash.image_details.domain

import androidx.annotation.WorkerThread
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage

interface ImageDetailsRepository {

    @WorkerThread
    fun getPhotoById(
        photoId: String,
        result: (Image) -> Unit,
        fail: (String?) -> Unit
    )

    @WorkerThread
    suspend fun addFavoriteImage(favoriteImage: FavoriteImage)
}