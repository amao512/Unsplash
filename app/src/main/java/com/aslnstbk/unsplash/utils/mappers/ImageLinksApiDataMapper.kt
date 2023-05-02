package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.ImageLinks
import com.aslnstbk.unsplash.common.data.models.api.ImageLinksApiData

class ImageLinksApiDataMapper {

    fun map(imageLinksApiData: ImageLinksApiData?): ImageLinks {
        return ImageLinks(
            self = imageLinksApiData?.self.orEmpty(),
            html = imageLinksApiData?.html.orEmpty(),
            download = imageLinksApiData?.download.orEmpty(),
            download_location = imageLinksApiData?.download_location.orEmpty()
        )
    }
}