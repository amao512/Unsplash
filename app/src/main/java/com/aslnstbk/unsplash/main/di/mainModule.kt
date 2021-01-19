package com.aslnstbk.unsplash.main.di

import com.aslnstbk.unsplash.main.MainRouter
import org.koin.dsl.module

val mainModule = module {

    factory {
        MainRouter()
    }
}