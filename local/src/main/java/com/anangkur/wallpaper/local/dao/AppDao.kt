package com.anangkur.wallpaper.local.dao

import androidx.room.*
import com.anangkur.wallpaper.local.model.DatabaseEntity

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallpaper(wallpaper: DatabaseEntity)

    @Query("SELECT * FROM DatabaseEntity")
    fun loadAllWallpaper(): List<DatabaseEntity>

    @Query("DELETE FROM DatabaseEntity WHERE id = :id")
    fun deleteWallpaperById(id: String)
}