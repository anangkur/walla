package com.anangkur.wallpaper.data

import com.anangkur.wallpaper.data.repository.LocalRepository
import com.anangkur.wallpaper.data.repository.RemoteRepository
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.domain.model.Collection as ModelCollection
import com.anangkur.wallpaper.domain.repository.Repository as RepositoryContract

class Repository (
    private val factory: RepositoryFactory
) : RepositoryContract {

    companion object{
        private var INSTANCE: Repository? = null
        fun getInstance(
            localRepository: LocalRepository,
            remoteRepository: RemoteRepository
        ) = INSTANCE ?: Repository(
            RepositoryFactory.getInstance(localRepository, remoteRepository)
        )
    }

    override suspend fun retrieveSavedWallpaper(): List<Wallpaper> {
        return factory.retrieveCacheDataStore().retrieveSavedWallpapers()
    }

    override suspend fun retrieveWallpapers(): List<Wallpaper> {
        return factory.retrieveCacheDataStore().retrieveWallpapers()
    }

    override suspend fun insertWallpaper(wallpaper: Wallpaper, isReplace: Boolean) {
        factory.retrieveCacheDataStore().insertWallpaper(wallpaper, isReplace)
    }

    override suspend fun deleteWallpaper(id: String) {
        factory.retrieveCacheDataStore().deleteWallpaper(id)
    }

    override suspend fun fetchWallpapers(clientId: String): List<Wallpaper> {
        return factory.retrieveRemoteDataStore().fetchWallpaper(clientId)
    }

    override suspend fun fetchCollection(clientId: String): List<ModelCollection> {
        return factory.retrieveRemoteDataStore().fetchCollection(clientId)
    }

    override suspend fun searchWallpaper(query: String): List<Wallpaper> {
        return factory.retrieveCacheDataStore().searchWallpaper(query)
    }

    override suspend fun fetchCollections(clientId: String, page: Int, perPage: Int): List<ModelCollection> {
        return factory.retrieveRemoteDataStore().fetchCollections(clientId, page, perPage)
    }

    override suspend fun fetchCollectionPhotos(clientId: String, collectionId: String): List<Wallpaper> {
        return factory.retrieveRemoteDataStore().fetchCollectionPhotos(clientId, collectionId)
    }

    override suspend fun fetchSearchByQuery(clientId: String, query: String): List<Wallpaper> {
        return factory.retrieveRemoteDataStore().fetchSearchPhotosByQuery(clientId, query)
    }

    override suspend fun fetchSearchByColor(clientId: String, color: String): List<Wallpaper> {
        return factory.retrieveRemoteDataStore().fetchSearchPhotosByColor(clientId, color)
    }

}