package com.aslnstbk.unsplash.common.data

import android.content.Context
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.stfalcon.imageviewer.StfalconImageViewer

class ImageViewer(
    private val imageLoader: ImageLoader
) {

    fun show(
        context: Context,
        images: List<String>
    ) {
        StfalconImageViewer.Builder(context, images) { view, image ->
            imageLoader.load(
                url = image,
                target = view,
                withCenterCrop = false
            )
        }.show()
    }
}