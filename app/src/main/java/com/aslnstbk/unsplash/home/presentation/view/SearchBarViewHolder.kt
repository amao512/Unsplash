package com.aslnstbk.unsplash.home.presentation.view

import android.view.View
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem
import com.aslnstbk.unsplash.home.presentation.models.SearchBarItem

class SearchBarViewHolder(
    itemView: View
): BaseViewHolder<HomeListItem>(itemView) {

    override fun onBind(data: HomeListItem) {
        val searchBarItem = (data as? SearchBarItem)?.data ?: return
    }
}