package com.aslnstbk.unsplash.image_details.di

import com.aslnstbk.unsplash.common.data.retrofit.RetrofitClient
import com.aslnstbk.unsplash.image_details.data.DefaultImageDetailsRepository
import com.aslnstbk.unsplash.image_details.data.ImageDetailsDataSource
import com.aslnstbk.unsplash.image_details.data.ImageDownload
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val imageDetailsModule = module {

    viewModel {
        ImageDetailsViewModel(
            imageDetailsRepository = get(),
            imageDownload = get()
        )
    }

    single {
        val retrofit: Retrofit = RetrofitClient.instance

        retrofit.create(ImageDetailsDataSource::class.java)
    }

    single {
        ImageDownload()
    }

    factory {
        DefaultImageDetailsRepository(
            imageDetailsDataSource = get(),
            appDatabase = get(),
            imageApiDataMapper = get()
        ) as ImageDetailsRepository
    }
}