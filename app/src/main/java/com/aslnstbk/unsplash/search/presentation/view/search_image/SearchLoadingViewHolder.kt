package com.aslnstbk.unsplash.search.presentation.view.search_image

import android.view.View
import com.aslnstbk.unsplash.databinding.SearchMoreLoadingItemBinding
import com.aslnstbk.unsplash.search.presentation.models.MoreLoadingItem
import com.aslnstbk.unsplash.search.presentation.models.SearchItem
import com.aslnstbk.unsplash.search.presentation.view.BaseViewHolder
import com.aslnstbk.unsplash.search.presentation.view.SearchListener
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show

class SearchLoadingViewHolder(
    itemView: View,
    private val searchListener: SearchListener
) : BaseViewHolder<SearchItem>(itemView) {

    private val binding = SearchMoreLoadingItemBinding.bind(itemView)

    override fun onBind(data: SearchItem) = with(binding) {
        val isLoading: Boolean = (data as? MoreLoadingItem)?.isLoading ?: return
        val isError: Boolean = (data as? MoreLoadingItem)?.isError ?: return

        when (isLoading) {
            true -> progressBar.show()
            false -> progressBar.hide()
        }

        when (isError) {
            true -> errorLayout.show()
            false -> errorLayout.hide()
        }

        retryTextView.setOnClickListener {
            searchListener.onMoreRetryClick()
        }
    }
}