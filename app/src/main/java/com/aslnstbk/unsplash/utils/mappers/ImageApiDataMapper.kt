package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.constants.Constants.EMPTY
import com.aslnstbk.unsplash.common.data.models.*
import com.aslnstbk.unsplash.common.data.models.api.ImageApiData
import com.aslnstbk.unsplash.utils.extensions.orZero

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
                id = it.id.orEmpty(),
                created_at = it.created_at.orEmpty(),
                updated_at = it.updated_at.orEmpty(),
                promoted_at = it.promoted_at.orEmpty(),
                width = it.width.orZero(),
                height = it.height.orZero(),
                color = it.color.orEmpty(),
                blur_hash = it.blur_hash.orEmpty(),
                description = it.description.orEmpty(),
                alt_description = it.alt_description.orEmpty(),
                urls = imageUrlsApiDataMapper.map(it.urls),
                links = imageLinksApiDataMapper.map(it.links),
                categories = it.categories ?: Any(),
                likes = it.likes.orZero(),
                user = imageUserApiDataMapper.map(it.user),
                tags = imageTagApiDataMapper.map(it.tags)
            )
        }
    }

    fun map(imageApiData: ImageApiData?): Image {
        return Image(
            id = imageApiData?.id.orEmpty(),
            created_at = imageApiData?.created_at.orEmpty(),
            updated_at = imageApiData?.updated_at.orEmpty(),
            promoted_at = imageApiData?.promoted_at.orEmpty(),
            width = imageApiData?.width.orZero(),
            height = imageApiData?.height.orZero(),
            color = imageApiData?.color.orEmpty(),
            blur_hash = imageApiData?.blur_hash.orEmpty(),
            description = imageApiData?.description.orEmpty(),
            alt_description = imageApiData?.alt_description.orEmpty(),
            urls = imageUrlsApiDataMapper.map(imageApiData?.urls),
            links = imageLinksApiDataMapper.map(imageApiData?.links),
            categories = imageApiData?.categories ?: EMPTY,
            likes = imageApiData?.likes.orZero(),
            user = imageUserApiDataMapper.map(imageApiData?.user),
            tags = imageTagApiDataMapper.map(imageApiData?.tags)
        )
    }
}