package com.aslnstbk.unsplash.home.di

import com.aslnstbk.unsplash.common.data.retrofit.RetrofitClient
import com.aslnstbk.unsplash.home.data.DefaultHomeRepository
import com.aslnstbk.unsplash.home.data.HomeDataSource
import com.aslnstbk.unsplash.home.domain.HomeRepository
import com.aslnstbk.unsplash.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            homeRepository = get()
        )
    }

    single {
        val retrofit = RetrofitClient.instance

        retrofit.create(HomeDataSource::class.java)
    }

    single {
        DefaultHomeRepository(
            homeDataSource = get(),
            imageApiDataMapper = get(),
            searchResultApiDataMapper = get()
        ) as HomeRepository
    }
}