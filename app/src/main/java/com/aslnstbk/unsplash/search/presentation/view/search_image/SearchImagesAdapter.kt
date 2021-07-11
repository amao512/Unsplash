package com.aslnstbk.unsplash.search.presentation.view.search_image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.home.data.ImageClickListener
import com.aslnstbk.unsplash.search.presentation.models.MoreLoadingItem
import com.aslnstbk.unsplash.search.presentation.models.SEARCH_IMAGE_ITEM_TYPE
import com.aslnstbk.unsplash.search.presentation.models.SearchItem
import com.aslnstbk.unsplash.search.presentation.view.BaseViewHolder
import com.aslnstbk.unsplash.search.presentation.view.SearchListener

class SearchImagesAdapter(
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener,
    private val searchListener: SearchListener
) : RecyclerView.Adapter<BaseViewHolder<SearchItem>>() {

    private val searchItemList: MutableList<SearchItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SearchItem> {
        return when (viewType){
            SEARCH_IMAGE_ITEM_TYPE -> createSearchImageViewHolder(parent)
            else -> createSearchLoadingViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SearchItem>, position: Int) {
        holder.onBind(searchItemList[position])
    }

    override fun getItemCount(): Int = searchItemList.size

    override fun getItemViewType(position: Int): Int = searchItemList[position].type

    fun setList(searchItemList: List<SearchItem>) {
        this.searchItemList.clear()
        this.searchItemList.addAll(searchItemList)
        notifyDataSetChanged()
    }

    fun setMoreList(searchItemList: List<SearchItem>) {
        this.searchItemList.addAll(searchItemList)
        notifyDataSetChanged()
    }

    fun hideLoadingItem(){
        this.searchItemList.removeLast()
        notifyDataSetChanged()
    }

    fun changeMoreLoadingItem(
        isLoading: Boolean,
        isError: Boolean
    ){
        this.searchItemList[searchItemList.lastIndex] = MoreLoadingItem(isLoading, isError)
        notifyDataSetChanged()
    }

    private fun createSearchImageViewHolder(parent: ViewGroup): BaseViewHolder<SearchItem> {
        return SearchImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_item,
                parent,
                false
            ),
            imageLoader,
            imageClickListener
        )
    }

    private fun createSearchLoadingViewHolder(parent: ViewGroup): BaseViewHolder<SearchItem> {
        return SearchLoadingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_more_loading_item,
                parent,
                false
            ),
            searchListener
        )
    }
}