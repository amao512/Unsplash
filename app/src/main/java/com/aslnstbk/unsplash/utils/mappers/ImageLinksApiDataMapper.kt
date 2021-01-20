package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.ImageLinks
import com.aslnstbk.unsplash.common.data.models.api.ImageLinksApiData

class ImageLinksApiDataMapper {

    fun map(imageLinksApiData: ImageLinksApiData?): ImageLinks {
        return ImageLinks(
            self = imageLinksApiData?.self ?: EMPTY_STRING,
            html = imageLinksApiData?.html ?: EMPTY_STRING,
            download = imageLinksApiData?.download ?: EMPTY_STRING,
            download_location = imageLinksApiData?.download_location ?: EMPTY_STRING
        )
    }
}