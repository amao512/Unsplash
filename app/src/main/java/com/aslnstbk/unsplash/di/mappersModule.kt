package com.aslnstbk.unsplash.di

import com.aslnstbk.unsplash.utils.mappers.PhotoApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.PhotoLinksApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.PhotoUrlsApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.PhotoUserApiDataMapper
import com.aslnstbk.unsplash.utils.mappers.UserProfileImageApiDataMapper
import org.koin.dsl.module

val mappersModule = module {

    single {
        PhotoApiDataMapper(
            photoUrlsApiDataMapper = get(),
            photoLinksApiDataMapper = get(),
            photoUserApiDataMapper = get()
        )
    }

    single {
        PhotoLinksApiDataMapper()
    }

    single {
        PhotoUrlsApiDataMapper()
    }

    single {
        PhotoUserApiDataMapper(
            userProfileImageApiDataMapper = get()
        )
    }

    single {
        UserProfileImageApiDataMapper()
    }
}