package com.aslnstbk.unsplash.di

import com.aslnstbk.unsplash.common.data.DefaultImageLoader
import com.aslnstbk.unsplash.common.data.ImageViewer
import com.aslnstbk.unsplash.common.data.retrofit.RetrofitClient
import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.common.domain.ImageLoader
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {

    single {
        AppDatabase.getInstance(
            context = androidContext()
        )
    }

    factory {
        DefaultImageLoader() as ImageLoader
    }

    factory {
        ImageViewer(
            imageLoader = get()
        )
    }

    single {
        RetrofitClient.instance(androidApplication())
    }
}