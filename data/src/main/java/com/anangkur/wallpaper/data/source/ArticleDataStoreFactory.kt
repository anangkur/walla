package com.anangkur.wallpaper.data.source

import com.anangkur.wallpaper.data.repository.ArticleDataStore
import com.anangkur.wallpaper.data.repository.ArticleLocal
import com.anangkur.wallpaper.data.repository.ArticleRemote

class ArticleDataStoreFactory (
    private val articleLocalDataStore: ArticleLocalDataStore,
    private val articleRemoteDataStore: ArticleRemoteDataStore
) {

    companion object{
        private var INSTANCE: ArticleDataStoreFactory? = null
        fun getInstance(
            articleLocal: ArticleLocal,
            articleRemote: ArticleRemote
        ) = INSTANCE ?: ArticleDataStoreFactory(
            ArticleLocalDataStore.getInstance(articleLocal),
            ArticleRemoteDataStore.getInstance(articleRemote)
        )
    }

    fun retrieveCacheDataStore(): ArticleDataStore {
        return articleLocalDataStore
    }

    fun retrieveRemoteDataStore(): ArticleDataStore {
        return articleRemoteDataStore
    }
}