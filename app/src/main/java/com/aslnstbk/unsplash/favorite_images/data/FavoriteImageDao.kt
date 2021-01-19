package com.aslnstbk.unsplash.favorite_images.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage

@Dao
interface FavoriteImageDao {

    @Query("SELECT * FROM favorite_image_table")
    fun getAllFavoriteImage(): LiveData<List<FavoriteImage>>

    @Query("SELECT * FROM favorite_image_table WHERE id = :id")
    fun getById(id: Int): FavoriteImage

    @Insert
    suspend fun insert(favoriteImage: FavoriteImage)

    @Update
    suspend fun update(favoriteImage: FavoriteImage)

    @Delete
    suspend fun delete(favoriteImage: FavoriteImage)
}