package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.PhotoUrls
import com.aslnstbk.unsplash.common.data.models.api.PhotoUrlsApiData

class PhotoUrlsApiDataMapper {

    fun map(photoUrlsApiData: PhotoUrlsApiData?): PhotoUrls {
        return PhotoUrls(
            raw = photoUrlsApiData?.raw ?: EMPTY_STRING,
            full = photoUrlsApiData?.full ?: EMPTY_STRING,
            regular = photoUrlsApiData?.regular ?: EMPTY_STRING,
            small = photoUrlsApiData?.small ?: EMPTY_STRING,
            thumb = photoUrlsApiData?.thumb ?: EMPTY_STRING
        )
    }
}