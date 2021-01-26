package com.aslnstbk.unsplash.search.presentation.view.query

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.search.data.models.QueryHistory
import com.aslnstbk.unsplash.search.presentation.view.SearchListener

class QueryHistoryViewHolder(
    itemView: View,
    private val searchListener: SearchListener
) : RecyclerView.ViewHolder(itemView) {

    private val queryTextView: TextView = itemView.findViewById(R.id.search_history_item_text_view_query)
    private val deleteImageView: ImageView = itemView.findViewById(R.id.search_history_item_delete)

    fun onBind(queryHistory: QueryHistory) {
        queryTextView.text = queryHistory.query

        queryTextView.setOnClickListener {
            searchListener.onQueryHistoryClick(query = queryTextView.text.toString())
        }

        deleteImageView.setOnClickListener {
            searchListener.onQueryHistoryDelete(
                queryHistory = queryHistory,
                position = layoutPosition
            )
        }
    }
}