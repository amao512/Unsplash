package com.aslnstbk.unsplash.history.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aslnstbk.unsplash.history.data.models.History

@Dao
interface HistoryDao {

    @Query("SELECT * FROM table_history")
    fun getAllHistory(): List<History>

    @Query("SELECT * FROM table_history WHERE imageId = :imageId")
    fun getById(imageId: String): History

    @Insert
    fun insert(history: History)

    @Update
    fun update(history: History)

    @Query("DELETE FROM table_history WHERE imageId = :imageId")
    fun delete(imageId: String)
}