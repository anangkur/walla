package com.anangkur.wallpaper.data.source

import com.anangkur.wallpaper.data.model.ArticleEntity
import com.anangkur.wallpaper.data.repository.ArticleDataStore
import com.anangkur.wallpaper.data.repository.ArticleLocal

class ArticleLocalDataStore (private val articleLocal: ArticleLocal): ArticleDataStore {

    companion object{
        private var INSTANCE: ArticleLocalDataStore? = null
        fun getInstance(articleLocal: ArticleLocal) = INSTANCE ?: ArticleLocalDataStore(articleLocal)
    }

    override suspend fun insertData(data: List<ArticleEntity>) {
        articleLocal.insertData(data)
    }

    override suspend fun deleteByCategory(category: String) {
        articleLocal.deleteByCategory(category)
    }

    override fun getAllDataByCategory(category: String): List<ArticleEntity> {
        return articleLocal.getAllDataByCategory(category)
    }

    override suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<ArticleEntity> {
        throw UnsupportedOperationException()
    }

}