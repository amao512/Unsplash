package com.aslnstbk.unsplash.image_details.domain

import com.aslnstbk.unsplash.common.data.models.Photo

interface ImageDetailsRepository {

    fun getPhotoById(
        photoId: String,
        result: (Photo) -> Unit,
        fail: (String?) -> Unit
    )
}