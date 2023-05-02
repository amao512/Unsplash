package com.aslnstbk.unsplash.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslnstbk.unsplash.R

lateinit var APP_ACTIVITY: MainActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY = this
    }
}