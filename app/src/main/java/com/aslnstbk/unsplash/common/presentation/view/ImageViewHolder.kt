package com.aslnstbk.unsplash.common.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.databinding.ImageItemBinding
import com.aslnstbk.unsplash.home.data.ImageClickListener

class ImageViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ImageItemBinding.bind(itemView)

    fun onBind(image: ImageItem) = with(binding) {
        imageLoader.load(
            url = image.imageUrl,
            target = imageView
        )

        imageView.setOnClickListener {
            imageClickListener.onImageClick(imageId = image.imageId)
        }
    }
}