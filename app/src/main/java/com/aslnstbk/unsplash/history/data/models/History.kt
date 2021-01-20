package com.aslnstbk.unsplash.history.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageId: String,
    val imageUrl: String,
)