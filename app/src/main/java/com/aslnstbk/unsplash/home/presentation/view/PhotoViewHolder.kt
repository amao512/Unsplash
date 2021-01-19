package com.aslnstbk.unsplash.home.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.data.DefaultImageLoader
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.models.Photo
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.models.PhotoListItem

class PhotoViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader = DefaultImageLoader()
) : BaseViewHolder<HomeListItem>(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.photo_item_image)
    private val urlTextView: TextView = itemView.findViewById(R.id.photo_item_url)

    override fun onBind(data: HomeListItem){
        val photo: Photo = (data as? PhotoListItem)?.data ?: return
        val small = photo.urls.small

        imageLoader.loadImage(
            url = small,
            target = imageView
        )
    }
}