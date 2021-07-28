package com.anangkur.wallpaper.data

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

    suspend fun retrieveWallpapers(): List<Wallpaper> {
        return factory.retrieveCacheDataStore().retrieveWallpapers()
    }

    suspend fun insertWallpaper(wallpaper: Wallpaper) {
        factory.retrieveCacheDataStore().insertWallpaper(wallpaper)
    }

}