package com.anangkur.wallpaper.data

import com.anangkur.wallpaper.data.repository.LocalDataStore
import com.anangkur.wallpaper.data.repository.RemoteDataStore
import com.anangkur.wallpaper.data.source.DataStoreFactory
import com.anangkur.wallpaper.domain.repository.Repository

class DataRepository (
    private val factory: DataStoreFactory
): Repository {

    companion object{
        private var INSTANCE: DataRepository? = null
        fun getInstance(
            localDataStore: LocalDataStore,
            remoteDataStore: RemoteDataStore
        ) = INSTANCE ?: DataRepository(
            DataStoreFactory.getInstance(localDataStore, remoteDataStore)
        )
    }

}