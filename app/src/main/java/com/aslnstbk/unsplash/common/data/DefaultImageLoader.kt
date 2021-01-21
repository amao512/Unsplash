package com.aslnstbk.unsplash.common.data

import android.widget.ImageView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.bumptech.glide.Glide

class DefaultImageLoader : ImageLoader {

    override fun load(
        url: String,
        target: ImageView,
        withCenterCrop: Boolean
    ) {
        if (withCenterCrop){
            Glide.with(target.context)
                .load(url)
                .placeholder(R.drawable.ic_default_thumbnail)
                .centerCrop()
                .into(target)
        } else {
            Glide.with(target.context)
                .load(url)
                .placeholder(R.drawable.ic_default_thumbnail)
                .into(target)
        }
    }
}