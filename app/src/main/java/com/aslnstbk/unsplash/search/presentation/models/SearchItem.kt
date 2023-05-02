package com.aslnstbk.unsplash.search.presentation.models

sealed class SearchItem (
    val type: SearchItemType
)

data class SearchImageItem (
    val imageId: String,
    val imageUrl: String
) : SearchItem(SearchItemType.SEARCH_IMAGE_ITEM_TYPE)

data class MoreLoadingItem (
    val isLoading: Boolean = true,
    val isError: Boolean = false
) : SearchItem(SearchItemType.MORE_LOADING_ITEM_TYPE)

enum class SearchItemType {
    SEARCH_IMAGE_ITEM_TYPE,
    MORE_LOADING_ITEM_TYPE
}