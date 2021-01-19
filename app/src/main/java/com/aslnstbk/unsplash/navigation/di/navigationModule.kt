package com.aslnstbk.unsplash.navigation.di

import com.aslnstbk.unsplash.navigation.Navigation
import org.koin.dsl.module

val navigationModule = module {

    single {
        Navigation()
    }
}