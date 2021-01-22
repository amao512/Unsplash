package com.aslnstbk.unsplash.search.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_search_history")
data class SearchHistory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val query: String
)