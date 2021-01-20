package com.aslnstbk.unsplash.di

import com.aslnstbk.unsplash.utils.mappers.PhotoApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.ImageLinksApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.ImageUrlsApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.ImageUserApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.UserProfilePhotoApiDataMapper
import org.koin.dsl.module

val mappersModule = module {

    single {
        PhotoApiDataMapper(
            imageUrlsApiDataMapper = get(),
            imageLinksApiDataMapper = get(),
            imageUserApiDataMapper = get()
        )
    }

    single {
        ImageLinksApiDataMapper()
    }

    single {
        ImageUrlsApiDataMapper()
    }

    single {
        ImageUserApiDataMapper(
            userProfilePhotoApiDataMapper = get()
        )
    }

    single {
        UserProfilePhotoApiDataMapper()
    }
}