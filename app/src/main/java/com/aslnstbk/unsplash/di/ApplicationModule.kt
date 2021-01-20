package com.aslnstbk.unsplash.di

import com.aslnstbk.unsplash.common.data.DefaultImageLoader
import com.aslnstbk.unsplash.common.data.retrofit.RetrofitClient
import com.aslnstbk.unsplash.common.data.retrofit.RetrofitDataSource
import com.aslnstbk.unsplash.common.data.room.AppDatabase
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.view.LoadingError
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    single {
        val retrofit = RetrofitClient.instance

        retrofit.create(RetrofitDataSource::class.java)
    }

    single {
        AppDatabase.getInstance(
            context = androidContext()
        )
    }

    single {
        LoadingError()
    }

    factory {
        DefaultImageLoader() as ImageLoader
    }
}