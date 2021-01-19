package com.aslnstbk.unsplash.navigation

import androidx.fragment.app.Fragment

abstract class Router {

    abstract var containerId: Int
    abstract var fragment: Fragment
    abstract var isBackStack: Boolean

    abstract fun setScreen(
        fragment: Fragment,
        isBackStack: Boolean = false
    ): Router
}