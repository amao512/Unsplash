package com.aslnstbk.unsplash.home.di

import com.aslnstbk.unsplash.home.data.DefaultHomeRepository
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
        DefaultHomeRepository(
            apiClient = get(),
            photoApiDataMapper = get()
        ) as HomeRepository
    }
}