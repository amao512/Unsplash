package com.aslnstbk.unsplash.di

import com.aslnstbk.unsplash.common.data.model.RetrofitClient
import com.aslnstbk.unsplash.common.data.model.RetrofitDataSource
import org.koin.dsl.module

val applicationModule = module {
    single {
        val retrofit = RetrofitClient.instance

        retrofit.create(RetrofitDataSource::class.java)
    }
}