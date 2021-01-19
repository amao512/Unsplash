package com.aslnstbk.unsplash.home.presentation.models

import com.aslnstbk.unsplash.common.data.models.Photo

const val PHOTOS_LIST_ITEM_TYPE = 0
const val SEARCH_BAR_ITEM_TYPE = 1

sealed class HomeListItem(
    val type: Int
)

class PhotoListItem(
    val data: Photo
): HomeListItem(PHOTOS_LIST_ITEM_TYPE)

class SearchBarItem(
    val data: Any
): HomeListItem(SEARCH_BAR_ITEM_TYPE)