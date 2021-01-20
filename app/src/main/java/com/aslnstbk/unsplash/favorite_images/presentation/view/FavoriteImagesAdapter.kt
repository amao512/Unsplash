package com.aslnstbk.unsplash.favorite_images.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.home.data.ImageClickListener

class FavoriteImagesAdapter(
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : RecyclerView.Adapter<FavoriteImageViewHolder>() {

    private val favoriteImageList: MutableList<FavoriteImage> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteImageViewHolder {
        return createFavoriteImageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FavoriteImageViewHolder, position: Int) {
        holder.onBind(favoriteImageList[position])
    }

    override fun getItemCount(): Int = favoriteImageList.size

    fun setList(favoriteImageList: List<FavoriteImage>){
        this.favoriteImageList.clear()
        this.favoriteImageList.addAll(favoriteImageList)
        notifyDataSetChanged()
    }

    private fun createFavoriteImageViewHolder(parent: ViewGroup): FavoriteImageViewHolder {
        return FavoriteImageViewHolder(
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