package com.aslnstbk.unsplash

import android.app.Application
import com.aslnstbk.unsplash.di.applicationModule
import com.aslnstbk.unsplash.di.mappersModule
import com.aslnstbk.unsplash.favorite_images.di.favoriteImagesModule
import com.aslnstbk.unsplash.history.di.historyModule
import com.aslnstbk.unsplash.home.di.homeModule
import com.aslnstbk.unsplash.image_details.di.imageDetailsModule
import com.aslnstbk.unsplash.main.di.mainModule
import com.aslnstbk.unsplash.navigation.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UnsplashApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@UnsplashApp)

            modules(
                applicationModule,
                mappersModule,
                navigationModule,
                mainModule,
                homeModule,
                imageDetailsModule,
                favoriteImagesModule,
                historyModule
            )
        }
    }
}