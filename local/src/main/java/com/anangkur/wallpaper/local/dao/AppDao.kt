package com.anangkur.wallpaper.local.dao

import androidx.room.*
import com.anangkur.wallpaper.local.model.DatabaseEntity

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWallpaper(wallpaper: DatabaseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallpaperReplace(wallpaper: DatabaseEntity)

    @Query("SELECT * FROM DatabaseEntity ORDER BY id")
    fun loadAllWallpaper(): List<DatabaseEntity>

    @Query("SELECT * FROM DatabaseEntity WHERE isSaved = :isSaved ORDER BY id")
    fun loadAllSavedWallpaper(isSaved: Boolean = true): List<DatabaseEntity>

    @Query("DELETE FROM DatabaseEntity WHERE id = :id")
    fun deleteWallpaperById(id: String)
}