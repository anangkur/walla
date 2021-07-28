package com.anangkur.wallpaper.data.repository

import com.anangkur.wallpaper.data.model.Wallpaper

interface LocalRepository {
    suspend fun deleteWallpaper(id: String)
    suspend fun insertWallpaper(wallpaper: Wallpaper)
    suspend fun retrieveWallpapers(): List<Wallpaper>
    fun isExpired(): Boolean
}