package com.aslnstbk.unsplash.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.home.presentation.HomeFragment
import com.aslnstbk.unsplash.navigation.Navigation
import org.koin.android.ext.android.inject

lateinit var APP_ACTIVITY: MainActivity

class MainActivity : AppCompatActivity() {

    private val navigation: Navigation by inject()
    private val mainRouter: MainRouter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this
        navigation.init(activity = this)

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) = navigation.navigate(
        mainRouter.setScreen(fragment)
    )
}