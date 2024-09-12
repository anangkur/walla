package com.anangkur.wallpaper.remote

import com.anangkur.wallpaper.data.repository.RemoteRepository
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.remote.mapper.toCollection
import com.anangkur.wallpaper.remote.mapper.toWallpaper
import com.anangkur.wallpaper.rest.di.provideRetrofitBuilder
import com.anangkur.wallpaper.rest.service.UnsplashService
import com.anangkur.wallpaper.domain.model.Collection as ModelCollection

class RemoteRepository(private val unsplashService: UnsplashService): RemoteRepository {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance(baseUrl: String) = INSTANCE ?: RemoteRepository(
            provideRetrofitBuilder(baseUrl).create(UnsplashService::class.java)
        )
    }

    override suspend fun fetchWallpaper(clientId: String): List<Wallpaper> {
        return unsplashService.getPhotos(clientId).map { it.toWallpaper() }
    }

    override suspend fun fetchCollection(clientId: String): List<ModelCollection> {
        return unsplashService.getCollections(clientId).map { it.toCollection() }
    }

    override suspend fun fetchCollections(clientId: String, page: Int, perPage: Int): List<ModelCollection> {
        return unsplashService.getCollections(clientId, page, perPage).map { it.toCollection() }
    }

    override suspend fun fetchCollectionPhotos(clientId: String, collectionId: String): List<Wallpaper> {
        return unsplashService.getCollectionPhotos(
            clientId = clientId,
            collectionId = collectionId
        ).map { it.toWallpaper() }
    }

    override suspend fun fetchSearchPhotosByColor(clientId: String, color: String): List<Wallpaper> {
        return unsplashService.getSearchPhotos(
            clientId = clientId,
            color = color
        ).results.map { it.toWallpaper() }
    }

    override suspend fun fetchSearchPhotosByQuery(clientId: String, query: String): List<Wallpaper> {
        return unsplashService.getSearchPhotos(
            clientId = clientId,
            query = query
        ).results.map { it.toWallpaper() }
    }
}