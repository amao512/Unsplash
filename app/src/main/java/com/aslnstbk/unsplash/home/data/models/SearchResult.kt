package com.aslnstbk.unsplash.home.data.models

import com.aslnstbk.unsplash.common.data.models.Image

data class SearchResult(
    val total: Int,
    val totalPages: Int,
    val results: List<Image>
)