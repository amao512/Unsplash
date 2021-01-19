package com.aslnstbk.unsplash.common.domain

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(
        url: String,
        target: ImageView
    )
}