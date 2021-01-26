package com.aslnstbk.unsplash.di

import com.aslnstbk.unsplash.utils.mappers.*
import org.koin.dsl.module

val mappersModule = module {

    single {
        ImageApiDataMapper(
            imageUrlsApiDataMapper = get(),
            imageLinksApiDataMapper = get(),
            imageUserApiDataMapper = get(),
            imageTagApiDataMapper = get()
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

    single {
        SearchResultApiDataMapper(
            imageApiDataMapper = get()
        )
    }

    single {
        ImageTagApiDataMapper()
    }
}