package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.models.*
import com.aslnstbk.unsplash.common.models.api.PhotoApiData

const val EMPTY_STRING = ""
const val DEFAULT_INT = 0

class PhotoApiDataMapper(
    private val photoUrlsApiDataMapper: PhotoUrlsApiDataMapper,
    private val photoLinksApiDataMapper: PhotoLinksApiDataMapper,
    private val photoUserApiDataMapper: PhotoUserApiDataMapper
) {

    fun map(photoApiDataList: List<PhotoApiData>?): List<Photo> {
        photoApiDataList ?: return emptyList()

        return photoApiDataList.map {
            Photo(
                id = it.id ?: EMPTY_STRING,
                created_at = it.created_at ?: EMPTY_STRING,
                updated_at = it.updated_at ?: EMPTY_STRING,
                promoted_at = it.promoted_at ?: EMPTY_STRING,
                width = it.width ?: DEFAULT_INT,
                height = it.height ?: DEFAULT_INT,
                color = it.color ?: EMPTY_STRING,
                blur_hash = it.blur_hash ?: EMPTY_STRING,
                description = it.description ?: EMPTY_STRING,
                alt_description = it.alt_description ?: EMPTY_STRING,
                urls = photoUrlsApiDataMapper.map(it.urls),
                links = photoLinksApiDataMapper.map(it.links),
                categories = it.categories,
                likes = it.likes ?: DEFAULT_INT,
                user = photoUserApiDataMapper.map(it.user)
            )
        }
    }
}