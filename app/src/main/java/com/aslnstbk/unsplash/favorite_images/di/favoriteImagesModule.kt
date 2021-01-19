package com.aslnstbk.unsplash.favorite_images.di

import com.aslnstbk.unsplash.favorite_images.data.DefaultFavoriteImageRepository
import com.aslnstbk.unsplash.favorite_images.domain.FavoriteImageRepository
import com.aslnstbk.unsplash.favorite_images.presentation.viewModel.FavoriteImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteImagesModule = module {

    viewModel {
        FavoriteImagesViewModel(
            favoriteImageRepository = get()
        )
    }

    factory {
        DefaultFavoriteImageRepository(
            appDatabase = get()
        ) as FavoriteImageRepository
    }
}