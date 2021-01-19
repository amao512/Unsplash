package com.aslnstbk.unsplash.common.data

import android.widget.ImageView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.bumptech.glide.Glide

class DefaultImageLoader : ImageLoader {

    override fun loadImage(
        url: String,
        target: ImageView
    ) {
        Glide.with(target.context)
            .load(url)
            .placeholder(R.drawable.ic_default_thumbnail)
            .centerCrop()
            .into(target)
    }
}