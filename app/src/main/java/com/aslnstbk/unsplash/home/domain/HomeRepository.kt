package com.aslnstbk.unsplash.home.domain

import androidx.annotation.WorkerThread
import com.aslnstbk.unsplash.common.models.Photo

interface HomeRepository {

    @WorkerThread
    fun getPhotos(
        result: (List<Photo>) -> Unit,
        fail: (String?) -> Unit
    )
}