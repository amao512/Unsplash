package com.aslnstbk.unsplash.common.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aslnstbk.unsplash.favorite_images.data.FavoriteImageDao
import com.aslnstbk.unsplash.favorite_images.data.models.FavoriteImage
import com.aslnstbk.unsplash.search.data.SearchHistoryDao
import com.aslnstbk.unsplash.search.data.models.SearchHistory

@Database(entities = [FavoriteImage::class, SearchHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteImageDao(): FavoriteImageDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        fun getInstance(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "unsplash-database"
        ).build()
    }
}