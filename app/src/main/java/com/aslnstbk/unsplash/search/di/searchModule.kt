package com.aslnstbk.unsplash.search.di

import com.aslnstbk.unsplash.search.data.DefaultSearchRepository
import com.aslnstbk.unsplash.search.data.SearchApiClient
import com.aslnstbk.unsplash.search.domain.SearchRepository
import com.aslnstbk.unsplash.search.presentation.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val searchModule = module {
    viewModel {
        SearchViewModel(
            searchRepository = get()
        )
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(SearchApiClient::class.java)
    }

    factory {
        DefaultSearchRepository(
            appDatabase = get(),
            searchApiClient = get(),
            searchResultApiDataMapper = get()
        ) as SearchRepository
    }
}