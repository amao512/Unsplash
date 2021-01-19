package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.*
import com.aslnstbk.unsplash.common.data.models.api.PhotoApiData

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

    fun map(photoApiData: PhotoApiData?): Photo {
        return Photo(
            id = photoApiData?.id ?: EMPTY_STRING,
            created_at = photoApiData?.created_at ?: EMPTY_STRING,
            updated_at = photoApiData?.updated_at ?: EMPTY_STRING,
            promoted_at = photoApiData?.promoted_at ?: EMPTY_STRING,
            width = photoApiData?.width ?: DEFAULT_INT,
            height = photoApiData?.height ?: DEFAULT_INT,
            color = photoApiData?.color ?: EMPTY_STRING,
            blur_hash = photoApiData?.blur_hash ?: EMPTY_STRING,
            description = photoApiData?.description ?: EMPTY_STRING,
            alt_description = photoApiData?.alt_description ?: EMPTY_STRING,
            urls = photoUrlsApiDataMapper.map(photoApiData?.urls),
            links = photoLinksApiDataMapper.map(photoApiData?.links),
            categories = photoApiData?.categories ?: EMPTY_STRING,
            likes = photoApiData?.likes ?: DEFAULT_INT,
            user = photoUserApiDataMapper.map(photoApiData?.user)
        )
    }
}