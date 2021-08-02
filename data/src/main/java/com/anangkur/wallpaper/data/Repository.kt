package com.anangkur.wallpaper.data

import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.data.repository.LocalRepository
import com.anangkur.wallpaper.data.repository.RemoteRepository

class Repository (
    private val factory: RepositoryFactory
) {

    companion object{
        private var INSTANCE: Repository? = null
        fun getInstance(
            localRepository: LocalRepository,
            remoteRepository: RemoteRepository
        ) = INSTANCE ?: Repository(
            RepositoryFactory.getInstance(localRepository, remoteRepository)
        )
    }

    suspend fun retrieveSavedWallpaper(): List<Wallpaper> {
        return factory.retrieveCacheDataStore().retrieveSavedWallpapers()
    }

    suspend fun retrieveWallpapers(): List<Wallpaper> {
        return factory.retrieveCacheDataStore().retrieveWallpapers()
    }

    suspend fun insertWallpaper(wallpaper: Wallpaper, isReplace: Boolean) {
        factory.retrieveCacheDataStore().insertWallpaper(wallpaper, isReplace)
    }

    suspend fun deleteWallpaper(id: String) {
        factory.retrieveCacheDataStore().deleteWallpaper(id)
    }

    suspend fun fetchWallpapers(clientId: String): List<Wallpaper> {
        return factory.retrieveRemoteDataStore().fetchWallpaper(clientId)
    }

    suspend fun fetchCollection(clientId: String): List<Collection> {
        return factory.retrieveRemoteDataStore().fetchCollection(clientId)
    }

    suspend fun searchWallpaper(query: String): List<Wallpaper> {
        return factory.retrieveCacheDataStore().searchWallpaper(query)
    }

    suspend fun fetchCollections(clientId: String, page: Int, perPage: Int): List<Collection> {
        return factory.retrieveRemoteDataStore().fetchCollections(clientId, page, perPage)
    }

    suspend fun fetchCollectionPhotos(clientId: String): List<Wallpaper> {
        return factory.retrieveRemoteDataStore().fetchCollectionPhotos(clientId)
    }

}