package com.aslnstbk.unsplash.main

import androidx.fragment.app.Fragment
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.navigation.Router

class MainRouter : Router() {

    override var containerId: Int = R.id.activity_main_fragment_container
    override lateinit var fragment: Fragment
    override var isBackStack: Boolean = false

    override fun setScreen(
        fragment: Fragment,
        isBackStack: Boolean
    ): Router {
        this.fragment = fragment
        this.isBackStack = isBackStack

        return this
    }
}