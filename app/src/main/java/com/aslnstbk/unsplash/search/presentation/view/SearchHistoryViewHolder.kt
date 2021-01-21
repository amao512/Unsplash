package com.aslnstbk.unsplash.search.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.search.data.models.SearchHistory

class SearchHistoryViewHolder(
    itemView: View,
    private val searchListener: SearchListener
) : RecyclerView.ViewHolder(itemView) {

    private val queryTextView: TextView = itemView.findViewById(R.id.search_history_item_text_view_query)
    private val deleteImageView: ImageView = itemView.findViewById(R.id.search_history_item_delete)

    fun onBind(searchHistory: SearchHistory) {
        queryTextView.text = searchHistory.query

        queryTextView.setOnClickListener {
            searchListener.onSearchHistoryClick(query = queryTextView.text.toString())
        }

        deleteImageView.setOnClickListener {
            searchListener.onSearchHistoryDelete(
                searchHistory = searchHistory,
                position = layoutPosition
            )
        }
    }
}