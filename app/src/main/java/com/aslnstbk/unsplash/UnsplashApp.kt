package com.aslnstbk.unsplash

import android.app.Application
import com.aslnstbk.unsplash.di.applicationModule
import com.aslnstbk.unsplash.di.mappersModule
import com.aslnstbk.unsplash.favorite_images.di.favoriteImagesModule
import com.aslnstbk.unsplash.home.di.homeModule
import com.aslnstbk.unsplash.image_details.di.imageDetailsModule
import com.aslnstbk.unsplash.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UnsplashApp: Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidContext(this@UnsplashApp)

            modules(
                applicationModule,
                mappersModule,
                homeModule,
                imageDetailsModule,
                favoriteImagesModule,
                searchModule
            )
        }
    }

    companion object {
        lateinit var instance: UnsplashApp
    }
}