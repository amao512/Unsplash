package com.aslnstbk.unsplash.search.presentation.view.search_image

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.aslnstbk.unsplash.R
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

    private val progressBar: ProgressBar = itemView.findViewById(R.id.search_more_loading_item_progress_bar)
    private val errorView: LinearLayout = itemView.findViewById(R.id.search_more_loading_item_error_view)
    private val retryTextView: TextView = itemView.findViewById(R.id.search_more_loading_item_retry_text_view)

    override fun onBind(data: SearchItem) {
        val isLoading: Boolean = (data as? MoreLoadingItem)?.isLoading ?: return
        val isError: Boolean = (data as? MoreLoadingItem)?.isError ?: return

        when (isLoading) {
            true -> progressBar.show()
            false -> progressBar.hide()
        }

        when (isError) {
            true -> errorView.show()
            false -> errorView.hide()
        }

        retryTextView.setOnClickListener {
            searchListener.onMoreRetryClick()
        }
    }
}