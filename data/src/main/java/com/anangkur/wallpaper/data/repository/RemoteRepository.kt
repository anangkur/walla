package com.anangkur.wallpaper.data.repository

import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.data.model.Wallpaper

interface RemoteRepository {
    suspend fun fetchWallpaper(): List<Wallpaper>
    suspend fun fetchCollection(): List<Collection>
}