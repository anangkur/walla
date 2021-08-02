package com.anangkur.wallpaper.remote

import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.data.repository.RemoteRepository
import com.anangkur.wallpaper.remote.model.unsplash.toCollection
import com.anangkur.wallpaper.remote.model.unsplash.toWallpaper
import com.anangkur.wallpaper.remote.services.UnsplashService

class RemoteRepository(private val unsplashService: UnsplashService): RemoteRepository {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance(unsplashService: UnsplashService) = INSTANCE ?: RemoteRepository(unsplashService)
    }

    override suspend fun fetchWallpaper(clientId: String): List<Wallpaper> {
        return unsplashService.getPhotos(clientId).map { it.toWallpaper() }
    }

    override suspend fun fetchCollection(clientId: String): List<Collection> {
        return unsplashService.getCollections(clientId).map { it.toCollection() }
    }

    override suspend fun fetchCollections(clientId: String, page: Int, perPage: Int): List<Collection> {
        return unsplashService.getCollections(clientId, page, perPage).map { it.toCollection() }
    }

    override suspend fun fetchCollectionPhotos(clientId: String): List<Wallpaper> {
        return unsplashService.getCollectionPhotos(clientId).map { it.toWallpaper() }
    }
}