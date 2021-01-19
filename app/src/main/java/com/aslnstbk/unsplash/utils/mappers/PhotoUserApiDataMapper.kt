package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.PhotoUser
import com.aslnstbk.unsplash.common.data.models.api.PhotoUserApiData

class PhotoUserApiDataMapper(
    private val userProfileImageApiDataMapper: UserProfileImageApiDataMapper
) {

    fun map(photoUserApiData: PhotoUserApiData?): PhotoUser {
        return PhotoUser(
            id = photoUserApiData?.id ?: EMPTY_STRING,
            updated_at = photoUserApiData?.updated_at ?: EMPTY_STRING,
            username = photoUserApiData?.username ?: EMPTY_STRING,
            name = photoUserApiData?.name ?: EMPTY_STRING,
            first_name = photoUserApiData?.first_name ?: EMPTY_STRING,
            last_name = photoUserApiData?.last_name ?: EMPTY_STRING,
            twitter_username = photoUserApiData?.twitter_username ?: EMPTY_STRING,
            portfolio_url = photoUserApiData?.portfolio_url ?: EMPTY_STRING,
            bio = photoUserApiData?.bio ?: EMPTY_STRING,
            location = photoUserApiData?.location ?: EMPTY_STRING,
            profile_image = userProfileImageApiDataMapper.map(photoUserApiData?.profile_image),
            instagram_username = photoUserApiData?.instagram_username ?: EMPTY_STRING,
            total_collections = photoUserApiData?.total_collections ?: DEFAULT_INT,
            total_likes = photoUserApiData?.total_likes ?: DEFAULT_INT,
            total_photos = photoUserApiData?.total_photos ?: DEFAULT_INT
        )
    }
}