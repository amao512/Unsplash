package com.aslnstbk.unsplash

import android.app.Application
import com.aslnstbk.unsplash.di.applicationModule
import com.aslnstbk.unsplash.di.mappersModule
import com.aslnstbk.unsplash.favorite_images.di.favoriteImagesModule
import com.aslnstbk.unsplash.home.di.homeModule
import com.aslnstbk.unsplash.image_details.di.imageDetailsModule
import com.aslnstbk.unsplash.navigation.di.navigationModule
import com.aslnstbk.unsplash.search.di.searchModule
import com.github.terrakok.cicerone.Cicerone
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UnsplashApp: Application() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidContext(this@UnsplashApp)

            modules(
                applicationModule,
                navigationModule,
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