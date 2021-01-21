package com.aslnstbk.unsplash.search.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aslnstbk.unsplash.search.data.models.SearchHistory

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM table_search_history")
    fun getAllSearchHistory(): List<SearchHistory>

    @Insert
    fun insert(searchHistory: SearchHistory)

    @Delete
    fun delete(searchHistory: SearchHistory)
}