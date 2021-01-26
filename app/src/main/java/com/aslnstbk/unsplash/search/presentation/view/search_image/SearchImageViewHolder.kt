package com.aslnstbk.unsplash.search.presentation.view.search_image

import android.view.View
import android.widget.ImageView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.search.presentation.models.SearchImageItem
import com.aslnstbk.unsplash.search.presentation.models.SearchItem
import com.aslnstbk.unsplash.search.presentation.view.BaseViewHolder

class SearchImageViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : BaseViewHolder<SearchItem>(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.image_item_image)

    override fun onBind(data: SearchItem) {
        val imageUrl: String = (data as? SearchImageItem)?.imageUrl ?: return
        val imageId: String = (data as? SearchImageItem)?.imageId ?: return

        imageLoader.load(
            url = imageUrl,
            target = imageView
        )

        itemView.setOnClickListener {
            imageClickListener.onImageClick(imageId = imageId)
        }
    }
}