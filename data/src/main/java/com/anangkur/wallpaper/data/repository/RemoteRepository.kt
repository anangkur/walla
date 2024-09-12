package com.anangkur.wallpaper.data.repository

import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.domain.model.Collection as ModelCollection

interface RemoteRepository {
    suspend fun fetchWallpaper(clientId: String): List<Wallpaper>
    suspend fun fetchCollection(clientId: String): List<ModelCollection>
    suspend fun fetchCollections(clientId: String, page: Int, perPage: Int): List<ModelCollection>
    suspend fun fetchCollectionPhotos(clientId: String, collectionId: String): List<Wallpaper>
    suspend fun fetchSearchPhotosByColor(clientId: String, color: String): List<Wallpaper>
    suspend fun fetchSearchPhotosByQuery(clientId: String, query: String): List<Wallpaper>
}