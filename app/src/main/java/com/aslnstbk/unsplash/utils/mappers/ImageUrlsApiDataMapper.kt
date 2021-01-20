package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.ImageUrls
import com.aslnstbk.unsplash.common.data.models.api.ImageUrlsApiData

class ImageUrlsApiDataMapper {

    fun map(imageUrlsApiData: ImageUrlsApiData?): ImageUrls {
        return ImageUrls(
            raw = imageUrlsApiData?.raw ?: EMPTY_STRING,
            full = imageUrlsApiData?.full ?: EMPTY_STRING,
            regular = imageUrlsApiData?.regular ?: EMPTY_STRING,
            small = imageUrlsApiData?.small ?: EMPTY_STRING,
            thumb = imageUrlsApiData?.thumb ?: EMPTY_STRING
        )
    }
}