package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.image_details.data.models.UserProfilePhoto
import com.aslnstbk.unsplash.image_details.data.models.UserProfilePhotoApiData

class UserProfilePhotoApiDataMapper {

    fun map(userProfilePhotoApiData: UserProfilePhotoApiData?): UserProfilePhoto {
        return UserProfilePhoto(
            small = userProfilePhotoApiData?.small.orEmpty(),
            medium = userProfilePhotoApiData?.medium.orEmpty(),
            large = userProfilePhotoApiData?.large.orEmpty(),
        )
    }
}