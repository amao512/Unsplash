package com.aslnstbk.unsplash.favorite_images.presentation.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.home.data.ImageClickListener

class FavoriteImageViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.image_item_image)

    fun onBind(favoriteImage: FavoriteImage){
        imageLoader.load(
            url = favoriteImage.imageUrl,
            target = imageView
        )

        itemView.setOnClickListener {
            imageClickListener.onImageClick(imageId = favoriteImage.imageId)
        }
    }
}