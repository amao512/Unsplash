package com.aslnstbk.unsplash.image_details.di

import com.aslnstbk.unsplash.common.data.model.RetrofitClient
import com.aslnstbk.unsplash.image_details.data.DefaultImageDetailsRepository
import com.aslnstbk.unsplash.image_details.data.ImageDetailsDataSource
import com.aslnstbk.unsplash.image_details.domain.ImageDetailsRepository
import com.aslnstbk.unsplash.image_details.presentation.viewModel.ImageDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val imageDetailsModule = module {

    viewModel {
        ImageDetailsViewModel(
            imageDetailsRepository = get()
        )
    }

    single {
        val retrofit: Retrofit = RetrofitClient.instance

        retrofit.create(ImageDetailsDataSource::class.java)
    }

    factory {
        DefaultImageDetailsRepository(
            imageDetailsDataSource = get(),
            photoApiDataMapper = get()
        ) as ImageDetailsRepository
    }
}