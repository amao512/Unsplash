package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.ImageUrls
import com.aslnstbk.unsplash.common.data.models.api.ImageUrlsApiData

class ImageUrlsApiDataMapper {

    fun map(imageUrlsApiData: ImageUrlsApiData?): ImageUrls {
        return ImageUrls(
            raw = imageUrlsApiData?.raw.orEmpty(),
            full = imageUrlsApiData?.full.orEmpty(),
            regular = imageUrlsApiData?.regular.orEmpty(),
            small = imageUrlsApiData?.small.orEmpty(),
            thumb = imageUrlsApiData?.thumb.orEmpty()
        )
    }
}