package com.aslnstbk.unsplash.navigation

import com.aslnstbk.unsplash.main.MainActivity

class Navigation {

    private lateinit var activity: MainActivity

    fun init(activity: MainActivity){
        this.activity = activity
    }

    fun navigate(router: Router){
        if(router.isBackStack){
            activity.supportFragmentManager.beginTransaction()
                .replace(router.containerId, router.fragment)
                .addToBackStack(null)
                .commit()
        } else {
            activity.supportFragmentManager.beginTransaction()
                .replace(router.containerId, router.fragment)
                .commit()
        }
    }

    fun back(){
        activity.supportFragmentManager.popBackStack()
    }
}