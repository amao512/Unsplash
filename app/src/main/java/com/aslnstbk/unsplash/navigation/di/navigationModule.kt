package com.aslnstbk.unsplash.navigation.di

import com.aslnstbk.unsplash.UnsplashApp
import org.koin.dsl.module

val navigationModule = module {

    single {
        UnsplashApp.instance.navigatorHolder
    }

    single {
        UnsplashApp.instance.router
    }
}