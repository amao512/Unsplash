package com.aslnstbk.unsplash.search.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aslnstbk.unsplash.search.data.models.QueryHistory

@Dao
interface QueryHistoryDao {

    @Query("SELECT * FROM table_search_history")
    fun getAllSearchHistory(): List<QueryHistory>

    @Query("SELECT * FROM table_search_history WHERE `query` = :query")
    fun getByQuery(query: String): QueryHistory

    @Insert
    fun insert(queryHistory: QueryHistory)

    @Delete
    fun delete(queryHistory: QueryHistory)
}