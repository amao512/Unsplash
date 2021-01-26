package com.aslnstbk.unsplash.search.presentation.view

import com.aslnstbk.unsplash.search.data.models.QueryHistory

interface SearchListener {

    fun onQueryHistoryDelete(
        queryHistory: QueryHistory,
        position: Int
    )

    fun onQueryHistoryClick(query: String)

    fun onMoreRetryClick()
}