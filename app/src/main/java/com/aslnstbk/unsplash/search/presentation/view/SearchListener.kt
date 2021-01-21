package com.aslnstbk.unsplash.search.presentation.view

import com.aslnstbk.unsplash.search.data.models.SearchHistory

interface SearchListener {

    fun onSearchHistoryDelete(
        searchHistory: SearchHistory,
        position: Int
    )

    fun onSearchHistoryClick(query: String)
}