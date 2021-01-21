package com.aslnstbk.unsplash.home.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.home.presentation.SearchListener
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.models.SEARCH_BAR_ITEM_TYPE

class HomeAdapter(
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener,
    private val searchListener: SearchListener
) : RecyclerView.Adapter<BaseViewHolder<HomeListItem>>() {

    private val homeListItemList: MutableList<HomeListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeListItem> {
        return when(viewType){
            SEARCH_BAR_ITEM_TYPE -> createSearchBarViewHolder(parent)
            else -> createImageViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<HomeListItem>, position: Int) {
        holder.onBind(homeListItemList[position])
    }

    override fun getItemViewType(position: Int): Int = homeListItemList[position].type

    override fun getItemCount(): Int = homeListItemList.size

    fun setPhotoList(homeListItemList: List<HomeListItem>){
        this.homeListItemList.clear()
        this.homeListItemList.addAll(homeListItemList)
        notifyDataSetChanged()
    }

    private fun createSearchBarViewHolder(parent: ViewGroup): BaseViewHolder<HomeListItem> {
        return SearchBarViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_bar_item,
                parent,
                false
            ),
            searchListener
        )
    }

    private fun createImageViewHolder(parent: ViewGroup): BaseViewHolder<HomeListItem> {
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