package com.anangkur.wallpaper.data

import com.anangkur.wallpaper.data.repository.LocalDataStore
import com.anangkur.wallpaper.data.repository.RemoteDataStore
import com.anangkur.wallpaper.data.source.DataStoreFactory
import com.anangkur.wallpaper.domain.model.Article
import com.anangkur.wallpaper.domain.repository.ArticleRepository

class DataRepository (
    private val factory: DataStoreFactory
): ArticleRepository {

    companion object{
        private var INSTANCE: DataRepository? = null
        fun getInstance(
            localDataStore: LocalDataStore,
            remoteDataStore: RemoteDataStore
        ) = INSTANCE ?: DataRepository(
            DataStoreFactory.getInstance(localDataStore, remoteDataStore)
        )
    }

    override suspend fun clearArticle(category: String) {
    }

    override suspend fun saveArticles(articles: List<Article>) {
    }

    override fun getArticlesLocal(category: String): List<Article> {
        return emptyList()
    }

    override suspend fun getArticleRemote(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<Article> {
        return emptyList()
    }

}