package com.aslnstbk.unsplash.common.data

import android.widget.ImageView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DefaultImageLoader : ImageLoader {

    override fun load(
        url: String,
        target: ImageView,
        withCenterCrop: Boolean
    ) {
        val requestOptions = if (withCenterCrop) {
            RequestOptions().transform(CenterCrop(), RoundedCorners(10))
        } else {
            RequestOptions().transform(FitCenter(), RoundedCorners(10))
        }

        Glide.with(target.context)
            .load(url)
            .thumbnail(
                Glide.with(target.context)
                    .load(R.drawable.ic_loading)
                    .apply(requestOptions)
            )
            .apply(requestOptions)
            .into(target)
    }
}