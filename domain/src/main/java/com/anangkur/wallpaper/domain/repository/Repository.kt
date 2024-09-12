package com.anangkur.wallpaper.domain.repository

import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.domain.model.Collection as ModelCollection

interface Repository {

    suspend fun retrieveSavedWallpaper(): List<Wallpaper>

    suspend fun retrieveWallpapers(): List<Wallpaper>

    suspend fun insertWallpaper(wallpaper: Wallpaper, isReplace: Boolean)

    suspend fun deleteWallpaper(id: String)

    suspend fun fetchWallpapers(clientId: String): List<Wallpaper>

    suspend fun fetchCollection(clientId: String): List<ModelCollection>

    suspend fun searchWallpaper(query: String): List<Wallpaper>

    suspend fun fetchCollections(clientId: String, page: Int, perPage: Int): List<ModelCollection>

    suspend fun fetchCollectionPhotos(clientId: String, collectionId: String): List<Wallpaper>

    suspend fun fetchSearchByQuery(clientId: String, query: String): List<Wallpaper>

    suspend fun fetchSearchByColor(clientId: String, color: String): List<Wallpaper>

}