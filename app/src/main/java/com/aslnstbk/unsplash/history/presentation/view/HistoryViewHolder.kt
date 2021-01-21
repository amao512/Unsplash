package com.aslnstbk.unsplash.history.presentation.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.history.data.models.History
import com.aslnstbk.unsplash.home.data.ImageClickListener

class HistoryViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.image_item_image)

    fun onBind(history: History){
        imageLoader.load(
            url = history.imageUrl,
            target = imageView
        )

        itemView.setOnClickListener {
            imageClickListener.onImageClick(imageId = history.imageId)
        }
    }
}