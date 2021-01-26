package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.ImageTag
import com.aslnstbk.unsplash.common.data.models.api.ImageTagApiData

class ImageTagApiDataMapper {

    fun map(tags: List<ImageTagApiData>?): List<ImageTag> {
        tags ?: return emptyList()

        return tags.map {
            ImageTag(
                title = it.title ?: EMPTY_STRING
            )
        }
    }
}