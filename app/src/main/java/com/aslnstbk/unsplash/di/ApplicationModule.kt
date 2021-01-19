package com.aslnstbk.unsplash.di

import com.aslnstbk.unsplash.common.data.DefaultImageLoader
import com.aslnstbk.unsplash.common.data.model.RetrofitClient
import com.aslnstbk.unsplash.common.data.model.RetrofitDataSource
import com.aslnstbk.unsplash.common.domain.ImageLoader
import org.koin.dsl.module

val applicationModule = module {
    single {
        val retrofit = RetrofitClient.instance

        retrofit.create(RetrofitDataSource::class.java)
    }

    factory {
        DefaultImageLoader() as ImageLoader
    }
}