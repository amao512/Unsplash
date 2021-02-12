package com.aslnstbk.unsplash.home.domain

import androidx.annotation.WorkerThread
import com.aslnstbk.unsplash.common.data.models.Image

interface HomeRepository {

    @WorkerThread
    suspend fun getImages(
        result: (List<Image>) -> Unit,
        fail: (String?) -> Unit
    )
}