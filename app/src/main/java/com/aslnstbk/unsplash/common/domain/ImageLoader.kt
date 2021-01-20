package com.aslnstbk.unsplash.common.domain

import android.widget.ImageView

interface ImageLoader {

    fun load(
        url: String,
        target: ImageView
    )
}