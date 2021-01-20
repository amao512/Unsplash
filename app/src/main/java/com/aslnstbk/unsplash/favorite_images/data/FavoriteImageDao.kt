package com.aslnstbk.unsplash.favorite_images.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage

@Dao
interface FavoriteImageDao {

    @Query("SELECT * FROM favorite_image_table")
    fun getAllFavoriteImage(): List<FavoriteImage>

    @Query("SELECT * FROM favorite_image_table WHERE imageId = :imageId")
    fun getById(imageId: String): FavoriteImage

    @Insert
    suspend fun insert(favoriteImage: FavoriteImage)

    @Update
    suspend fun update(favoriteImage: FavoriteImage)

    @Query("DELETE FROM favorite_image_table WHERE imageId = :imageId")
    suspend fun delete(imageId: String)
}