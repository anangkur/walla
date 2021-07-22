package com.anangkur.wallpaper.remote

import com.anangkur.wallpaper.data.model.ArticleEntity
import com.anangkur.wallpaper.data.repository.ArticleRemote

class RemoteRepository: ArticleRemote {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository()
    }

    override suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<ArticleEntity> {
        return emptyList()
    }
}