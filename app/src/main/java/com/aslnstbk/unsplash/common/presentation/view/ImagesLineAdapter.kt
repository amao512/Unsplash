package com.aslnstbk.unsplash.common.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.common.presentation.models.ImageItem
import com.aslnstbk.unsplash.home.data.ImageClickListener

class ImagesLineAdapter(
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : RecyclerView.Adapter<ImageViewHolder>() {

    private val imagesList: MutableList<ImageItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return createImageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(imagesList[position])
    }

    override fun getItemCount(): Int = imagesList.size

    fun setList(imagesList: List<ImageItem>){
        this.imagesList.clear()
        this.imagesList.addAll(imagesList)
        notifyDataSetChanged()
    }

    private fun createImageViewHolder(parent: ViewGroup): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_item,
                parent,
                false
            ),
            imageLoader,
            imageClickListener
        )
    }
}