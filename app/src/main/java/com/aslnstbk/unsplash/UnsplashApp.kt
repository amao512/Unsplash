package com.aslnstbk.unsplash

import android.app.Application
import com.aslnstbk.unsplash.di.applicationModule
import com.aslnstbk.unsplash.di.mappersModule
import com.aslnstbk.unsplash.home.di.homeModule
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
                homeModule
            )
        }
    }
}