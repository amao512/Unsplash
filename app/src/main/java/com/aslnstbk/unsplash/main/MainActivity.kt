package com.aslnstbk.unsplash.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.presentation.view.LoadingError
import com.aslnstbk.unsplash.navigation.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject

lateinit var APP_ACTIVITY: MainActivity

class MainActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()
    private val loadingError: LoadingError by inject()

    private val navigator: AppNavigator = AppNavigator(this, R.id.activity_main_fragment_container)

    private lateinit var favoriteFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this

        initViews()
        setStartFragment()
    }

    override fun onResumeFragments() {
        super.onStart()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onStop() {
        super.onStop()
        navigatorHolder.removeNavigator()
    }

    private fun initViews() {
        loadingError.init()

        favoriteFab = findViewById(R.id.activity_main_fab_favorite)
        favoriteFab.setOnClickListener {
            router.navigateTo(Screens.FavoriteImages())
        }
    }

    private fun setStartFragment() {
        router.replaceScreen(Screens.Home())
    }
}