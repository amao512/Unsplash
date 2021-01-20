package com.aslnstbk.unsplash.history.di

import com.aslnstbk.unsplash.history.data.DefaultHistoryRepository
import com.aslnstbk.unsplash.history.domain.HistoryRepository
import com.aslnstbk.unsplash.history.presentation.viewModel.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {

    viewModel {
        HistoryViewModel(
            historyRepository = get()
        )
    }

    factory {
        DefaultHistoryRepository(
            appDatabase = get()
        ) as HistoryRepository
    }
}