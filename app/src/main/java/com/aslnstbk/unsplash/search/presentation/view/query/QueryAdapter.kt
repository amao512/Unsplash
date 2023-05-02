package com.aslnstbk.unsplash.search.presentation.view.query

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.databinding.SearchHistoryItemBinding
import com.aslnstbk.unsplash.search.data.models.QueryHistory
import com.aslnstbk.unsplash.search.presentation.view.SearchListener

class QueryAdapter(
    private val searchListener: SearchListener
) : RecyclerView.Adapter<QueryHistoryViewHolder>() {

    private val queryHistoryList: MutableList<QueryHistory> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryHistoryViewHolder {
        return createSearchHistoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: QueryHistoryViewHolder, position: Int) {
        holder.onBind(queryHistoryList[position])
    }

    override fun getItemCount(): Int = queryHistoryList.size

    fun setList(queryHistoryList: List<QueryHistory>){
        this.queryHistoryList.clear()
        this.queryHistoryList.addAll(queryHistoryList)
        notifyDataSetChanged()
    }

    fun removeFromList(
        queryHistory: QueryHistory,
        position: Int
    ){
        this.queryHistoryList.remove(queryHistory)
        notifyItemRemoved(position)
    }

    private fun createSearchHistoryViewHolder(parent: ViewGroup): QueryHistoryViewHolder {
        return QueryHistoryViewHolder(
            SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root,
            searchListener
        )
    }
}