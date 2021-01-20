package com.aslnstbk.unsplash.favorite_images.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_image_table")
data class FavoriteImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageId: String,
    val imageUrl: String
)