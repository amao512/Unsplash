package com.aslnstbk.unsplash.home.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.models.PhotoListItem

class PhotoViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : BaseViewHolder<HomeListItem>(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.photo_item_image)
    private val urlTextView: TextView = itemView.findViewById(R.id.photo_item_url)

    override fun onBind(data: HomeListItem){
        val imageId: String = (data as? PhotoListItem)?.imageId ?: return
        val imageUrl: String = (data as? PhotoListItem)?.imageUrl ?: return

        imageLoader.loadImage(
            url = imageUrl,
            target = imageView
        )

        itemView.setOnClickListener {
            imageClickListener.onClick(imageId = imageId)
        }
    }
}