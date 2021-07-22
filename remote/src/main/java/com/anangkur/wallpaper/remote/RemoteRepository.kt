package com.anangkur.wallpaper.remote

import com.anangkur.wallpaper.data.repository.RemoteDataStore

class RemoteRepository: RemoteDataStore {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository()
    }
}