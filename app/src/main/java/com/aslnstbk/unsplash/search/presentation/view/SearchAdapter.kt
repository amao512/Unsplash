package com.aslnstbk.unsplash.search.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.search.data.models.SearchHistory

class SearchAdapter(
    private val searchListener: SearchListener
) : RecyclerView.Adapter<SearchHistoryViewHolder>() {

    private val searchHistoryList: MutableList<SearchHistory> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return createSearchHistoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.onBind(searchHistoryList[position])
    }

    override fun getItemCount(): Int = searchHistoryList.size

    fun setList(searchHistoryList: List<SearchHistory>){
        this.searchHistoryList.clear()
        this.searchHistoryList.addAll(searchHistoryList)
        notifyDataSetChanged()
    }

    fun removeFromList(
        searchHistory: SearchHistory,
        position: Int
    ){
        this.searchHistoryList.remove(searchHistory)
        notifyItemRemoved(position)
    }

    private fun createSearchHistoryViewHolder(parent: ViewGroup): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_history_item,
                parent,
                false
            ),
            searchListener
        )
    }
}