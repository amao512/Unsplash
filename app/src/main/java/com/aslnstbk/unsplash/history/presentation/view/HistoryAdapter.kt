package com.aslnstbk.unsplash.history.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.common.domain.ImageLoader
import com.aslnstbk.unsplash.history.data.models.History
import com.aslnstbk.unsplash.home.data.ImageClickListener

class HistoryAdapter(
    private val imageLoader: ImageLoader,
    private val imageClickListener: ImageClickListener
) : RecyclerView.Adapter<HistoryViewHolder>() {

    private val historyList: MutableList<History> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return createHistoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.onBind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size

    fun setList(historyList: List<History>){
        this.historyList.clear()
        this.historyList.addAll(historyList)
        notifyDataSetChanged()
    }

    private fun createHistoryViewHolder(parent: ViewGroup): HistoryViewHolder {
        return HistoryViewHolder(
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