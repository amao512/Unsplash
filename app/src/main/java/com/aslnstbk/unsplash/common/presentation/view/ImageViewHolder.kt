package com.aslnstbk.unsplash.common.presentation.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.home.data.ImageClickListener

class ImageViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.image_item_image)

    fun onBind(image: ImageItem){
        imageLoader.load(
            url = image.imageUrl,
            target = imageView
        )

        itemView.setOnClickListener {
            imageClickListener.onImageClick(imageId = image.imageId)
        }
    }
}