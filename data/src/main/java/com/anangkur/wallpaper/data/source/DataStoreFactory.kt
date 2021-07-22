package com.anangkur.wallpaper.data.source

import com.anangkur.wallpaper.data.repository.LocalDataStore
import com.anangkur.wallpaper.data.repository.RemoteDataStore

class DataStoreFactory (
    private val localDataStore: LocalDataStore,
    private val remoteDataStore: RemoteDataStore
) {

    companion object{
        private var INSTANCE: DataStoreFactory? = null
        fun getInstance(
            localDataStore: LocalDataStore,
            remoteDataStore: RemoteDataStore
        ) = INSTANCE ?: DataStoreFactory(localDataStore, remoteDataStore)
    }

    fun retrieveCacheDataStore(): LocalDataStore {
        return localDataStore
    }

    fun retrieveRemoteDataStore(): RemoteDataStore {
        return remoteDataStore
    }
}