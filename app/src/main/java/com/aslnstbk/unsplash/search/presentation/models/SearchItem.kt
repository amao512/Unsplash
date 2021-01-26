package com.aslnstbk.unsplash.search.presentation.models

const val SEARCH_IMAGE_ITEM_TYPE = 0
const val MORE_LOADING_ITEM_TYPE = 1

sealed class SearchItem (
    val type: Int
)

data class SearchImageItem (
    val imageId: String,
    val imageUrl: String
) : SearchItem(SEARCH_IMAGE_ITEM_TYPE)

data class MoreLoadingItem (
    val isLoading: Boolean = true,
    val isError: Boolean = false
) : SearchItem(MORE_LOADING_ITEM_TYPE)