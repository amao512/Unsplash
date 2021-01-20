package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.ImageUser
import com.aslnstbk.unsplash.common.data.models.api.ImageUserApiData

class ImageUserApiDataMapper(
    private val userProfilePhotoApiDataMapper: UserProfilePhotoApiDataMapper
) {

    fun map(imageUserApiData: ImageUserApiData?): ImageUser {
        return ImageUser(
            id = imageUserApiData?.id ?: EMPTY_STRING,
            updated_at = imageUserApiData?.updated_at ?: EMPTY_STRING,
            username = imageUserApiData?.username ?: EMPTY_STRING,
            name = imageUserApiData?.name ?: EMPTY_STRING,
            first_name = imageUserApiData?.first_name ?: EMPTY_STRING,
            last_name = imageUserApiData?.last_name ?: EMPTY_STRING,
            twitter_username = imageUserApiData?.twitter_username ?: EMPTY_STRING,
            portfolio_url = imageUserApiData?.portfolio_url ?: EMPTY_STRING,
            bio = imageUserApiData?.bio ?: EMPTY_STRING,
            location = imageUserApiData?.location ?: EMPTY_STRING,
            profile_photo = userProfilePhotoApiDataMapper.map(imageUserApiData?.profile_photo),
            instagram_username = imageUserApiData?.instagram_username ?: EMPTY_STRING,
            total_collections = imageUserApiData?.total_collections ?: DEFAULT_INT,
            total_likes = imageUserApiData?.total_likes ?: DEFAULT_INT,
            total_photos = imageUserApiData?.total_photos ?: DEFAULT_INT
        )
    }
}