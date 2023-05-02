package com.aslnstbk.unsplash.search.presentation.view.query

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.databinding.SearchHistoryItemBinding
import com.aslnstbk.unsplash.search.data.models.QueryHistory
import com.aslnstbk.unsplash.search.presentation.view.SearchListener

class QueryHistoryViewHolder(
    itemView: View,
    private val searchListener: SearchListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = SearchHistoryItemBinding.bind(itemView)

    fun onBind(queryHistory: QueryHistory) = with(binding) {
        queryTextView.text = queryHistory.query

        queryTextView.setOnClickListener {
            searchListener.onQueryHistoryClick(query = queryTextView.text.toString())
        }

        clearImageView.setOnClickListener {
            searchListener.onQueryHistoryDelete(
                queryHistory = queryHistory,
                position = layoutPosition
            )
        }
    }
}