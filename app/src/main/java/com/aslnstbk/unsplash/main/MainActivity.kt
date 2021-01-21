package com.aslnstbk.unsplash.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.presentation.view.LoadingError
import com.aslnstbk.unsplash.favorite_images.presentation.FavoriteImagesFragment
import com.aslnstbk.unsplash.home.presentation.HomeFragment
import com.aslnstbk.unsplash.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject

lateinit var APP_ACTIVITY: MainActivity

class MainActivity : AppCompatActivity() {

    private val navigation: Navigation by inject()
    private val mainRouter: MainRouter by inject()
    private val loadingError: LoadingError by inject()

    private lateinit var favoriteFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this
        navigation.init(activity = this)
        loadingError.init(activity = this)

        replaceFragment(HomeFragment())

        initViews()
    }

    private fun initViews() {
        favoriteFab = findViewById(R.id.activity_main_fab_favorite)
        favoriteFab.setOnClickListener {
            navigation.navigate(mainRouter.setScreen(
                fragment = FavoriteImagesFragment(),
                isBackStack = true
            ))
        }
    }

    private fun replaceFragment(fragment: Fragment) = navigation.navigate(
        mainRouter.setScreen(fragment)
    )
}