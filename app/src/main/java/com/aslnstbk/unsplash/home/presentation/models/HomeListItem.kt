package com.aslnstbk.unsplash.home.presentation.models

const val PHOTOS_LIST_ITEM_TYPE = 0
const val SEARCH_BAR_ITEM_TYPE = 1

sealed class HomeListItem(
    val type: Int
)

class PhotoListItem(
    val imageId: String,
    val imageUrl: String
): HomeListItem(PHOTOS_LIST_ITEM_TYPE)

class SearchBarItem(
    val data: Any
): HomeListItem(SEARCH_BAR_ITEM_TYPE)