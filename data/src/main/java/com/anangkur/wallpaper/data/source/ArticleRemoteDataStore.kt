package com.anangkur.wallpaper.data.source

import com.anangkur.wallpaper.data.model.ArticleEntity
import com.anangkur.wallpaper.data.repository.ArticleDataStore
import com.anangkur.wallpaper.data.repository.ArticleRemote

class ArticleRemoteDataStore (private val articleRemote: ArticleRemote): ArticleDataStore {

    companion object{
        private var INSTANCE: ArticleRemoteDataStore? = null
        fun getInstance(articleRemote: ArticleRemote) = INSTANCE ?: ArticleRemoteDataStore(articleRemote)
    }

    override suspend fun insertData(data: List<ArticleEntity>) {
        throw UnsupportedOperationException()
    }

    override suspend fun deleteByCategory(category: String) {
        throw UnsupportedOperationException()
    }

    override fun getAllDataByCategory(category: String): List<ArticleEntity> {
        throw UnsupportedOperationException()
    }

    override suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<ArticleEntity> {
        return articleRemote.getTopHeadlinesNews(apiKey, country, category, sources, q)
    }
}