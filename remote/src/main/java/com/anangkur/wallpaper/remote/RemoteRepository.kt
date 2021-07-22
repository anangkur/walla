package com.anangkur.wallpaper.remote

import com.anangkur.wallpaper.data.repository.RemoteRepository

class RemoteRepository: RemoteRepository {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository()
    }
}