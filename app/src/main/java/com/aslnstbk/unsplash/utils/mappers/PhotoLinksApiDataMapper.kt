package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.PhotoLinks
import com.aslnstbk.unsplash.common.data.models.api.PhotoLinksApiData

class PhotoLinksApiDataMapper {

    fun map(photoLinksApiData: PhotoLinksApiData?): PhotoLinks {
        return PhotoLinks(
            self = photoLinksApiData?.self ?: EMPTY_STRING,
            html = photoLinksApiData?.html ?: EMPTY_STRING,
            download = photoLinksApiData?.download ?: EMPTY_STRING,
            download_location = photoLinksApiData?.download_location ?: EMPTY_STRING
        )
    }
}