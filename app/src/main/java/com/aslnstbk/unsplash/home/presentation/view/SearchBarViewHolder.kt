package com.aslnstbk.unsplash.home.presentation.view

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.home.presentation.SearchListener
import com.aslnstbk.unsplash.home.presentation.models.HomeListItem

class SearchBarViewHolder(
    itemView: View,
    private val searchListener: SearchListener
): BaseViewHolder<HomeListItem>(itemView) {

    private val searchEditText: EditText = itemView.findViewById(R.id.search_bar_item_edit_text)

    override fun onBind(data: HomeListItem) {
        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                searchListener.onSearch(query = searchEditText.text.toString())

                return@setOnKeyListener true
            }

            false
        }
    }
}