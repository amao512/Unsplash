package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.*
import com.aslnstbk.unsplash.common.data.models.api.ImageApiData

const val EMPTY_STRING = ""
const val DEFAULT_INT = 0

class ImageApiDataMapper(
    private val imageUrlsApiDataMapper: ImageUrlsApiDataMapper,
    private val imageLinksApiDataMapper: ImageLinksApiDataMapper,
    private val imageUserApiDataMapper: ImageUserApiDataMapper,
    private val imageTagApiDataMapper: ImageTagApiDataMapper
) {

    fun map(imageApiDataList: List<ImageApiData>?): List<Image> {
        imageApiDataList ?: return emptyList()

        return imageApiDataList.map {
            Image(
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
                urls = imageUrlsApiDataMapper.map(it.urls),
                links = imageLinksApiDataMapper.map(it.links),
                categories = it.categories ?: Any(),
                likes = it.likes ?: DEFAULT_INT,
                user = imageUserApiDataMapper.map(it.user),
                tags = imageTagApiDataMapper.map(it.tags)
            )
        }
    }

    fun map(imageApiData: ImageApiData?): Image {
        return Image(
            id = imageApiData?.id ?: EMPTY_STRING,
            created_at = imageApiData?.created_at ?: EMPTY_STRING,
            updated_at = imageApiData?.updated_at ?: EMPTY_STRING,
            promoted_at = imageApiData?.promoted_at ?: EMPTY_STRING,
            width = imageApiData?.width ?: DEFAULT_INT,
            height = imageApiData?.height ?: DEFAULT_INT,
            color = imageApiData?.color ?: EMPTY_STRING,
            blur_hash = imageApiData?.blur_hash ?: EMPTY_STRING,
            description = imageApiData?.description ?: EMPTY_STRING,
            alt_description = imageApiData?.alt_description ?: EMPTY_STRING,
            urls = imageUrlsApiDataMapper.map(imageApiData?.urls),
            links = imageLinksApiDataMapper.map(imageApiData?.links),
            categories = imageApiData?.categories ?: EMPTY_STRING,
            likes = imageApiData?.likes ?: DEFAULT_INT,
            user = imageUserApiDataMapper.map(imageApiData?.user),
            tags = imageTagApiDataMapper.map(imageApiData?.tags)
        )
    }
}