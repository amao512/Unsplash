package com.aslnstbk.unsplash.home.domain

import androidx.annotation.WorkerThread
import com.aslnstbk.unsplash.common.data.models.Image
import com.aslnstbk.unsplash.home.data.models.SearchResult

interface HomeRepository {

    @WorkerThread
    fun getImages(
        result: (List<Image>) -> Unit,
        fail: (String?) -> Unit
    )

    @WorkerThread
    fun searchImages(
        query: String,
        result: (SearchResult) -> Unit,
        fail: (String?) -> Unit
    )
}