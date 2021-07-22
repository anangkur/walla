package com.anangkur.wallpaper.data

import com.anangkur.wallpaper.data.repository.LocalRepository
import com.anangkur.wallpaper.data.repository.RemoteRepository

class RepositoryFactory (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    companion object{
        private var INSTANCE: RepositoryFactory? = null
        fun getInstance(
            localRepository: LocalRepository,
            remoteRepository: RemoteRepository
        ) = INSTANCE ?: RepositoryFactory(localRepository, remoteRepository)
    }

    fun retrieveCacheDataStore(): LocalRepository {
        return localRepository
    }

    fun retrieveRemoteDataStore(): RemoteRepository {
        return remoteRepository
    }
}