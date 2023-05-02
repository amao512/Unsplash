package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.data.models.ImageUser
import com.aslnstbk.unsplash.common.data.models.api.ImageUserApiData
import com.aslnstbk.unsplash.utils.extensions.orZero

class ImageUserApiDataMapper(
    private val userProfilePhotoApiDataMapper: UserProfilePhotoApiDataMapper
) {

    fun map(imageUserApiData: ImageUserApiData?): ImageUser {
        return ImageUser(
            id = imageUserApiData?.id.orEmpty(),
            updated_at = imageUserApiData?.updated_at.orEmpty(),
            username = imageUserApiData?.username.orEmpty(),
            name = imageUserApiData?.name.orEmpty(),
            first_name = imageUserApiData?.first_name.orEmpty(),
            last_name = imageUserApiData?.last_name.orEmpty(),
            twitter_username = imageUserApiData?.twitter_username.orEmpty(),
            portfolio_url = imageUserApiData?.portfolio_url.orEmpty(),
            bio = imageUserApiData?.bio.orEmpty(),
            location = imageUserApiData?.location.orEmpty(),
            profile_photo = userProfilePhotoApiDataMapper.map(imageUserApiData?.profile_photo),
            instagram_username = imageUserApiData?.instagram_username.orEmpty(),
            total_collections = imageUserApiData?.total_collections.orZero(),
            total_likes = imageUserApiData?.total_likes.orZero(),
            total_photos = imageUserApiData?.total_photos.orZero()
        )
    }
}