package com.aslnstbk.unsplash.image_details.domain

import com.aslnstbk.unsplash.common.models.Photo

interface ImageDetailsRepository {

    fun getPhotoById(
        photoId: String,
        result: (Photo) -> Unit,
        fail: (String?) -> Unit
    )
}