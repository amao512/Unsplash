package com.aslnstbk.unsplash.utils.mappers

import com.aslnstbk.unsplash.common.models.UserProfileImage
import com.aslnstbk.unsplash.common.models.api.UserProfileImageApiData

class UserProfileImageApiDataMapper {

    fun map(userProfileImageApiData: UserProfileImageApiData?): UserProfileImage {
        return UserProfileImage(
            small = userProfileImageApiData?.small ?: EMPTY_STRING,
            medium = userProfileImageApiData?.medium ?: EMPTY_STRING,
            large = userProfileImageApiData?.large ?: EMPTY_STRING,
        )
    }
}