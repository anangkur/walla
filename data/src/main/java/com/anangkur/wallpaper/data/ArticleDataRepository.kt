package com.anangkur.wallpaper.data

import com.anangkur.wallpaper.data.mapper.ArticleMapper
import com.anangkur.wallpaper.data.repository.ArticleLocal
import com.anangkur.wallpaper.data.repository.ArticleRemote
import com.anangkur.wallpaper.data.source.ArticleDataStoreFactory
import com.anangkur.wallpaper.domain.model.Article
import com.anangkur.wallpaper.domain.repository.ArticleRepository

class ArticleDataRepository (
    private val factory: ArticleDataStoreFactory,
    private val mapper: ArticleMapper
): ArticleRepository {

    companion object{
        private var INSTANCE: ArticleDataRepository? = null
        fun getInstance(
            articleLocal: ArticleLocal,
            articleRemote: ArticleRemote
        ) = INSTANCE ?: ArticleDataRepository(
            ArticleDataStoreFactory.getInstance(articleLocal, articleRemote),
            ArticleMapper.getInstance()
        )
    }

    override suspend fun clearArticle(category: String) {
        factory.retrieveCacheDataStore().deleteByCategory(category)
    }

    override suspend fun saveArticles(articles: List<Article>) {
        factory.retrieveCacheDataStore().insertData(articles.map { mapper.mapToEntity(it) })
    }

    override fun getArticlesLocal(category: String): List<Article> {
        return factory.retrieveCacheDataStore().getAllDataByCategory(category).map {
            mapper.mapFromEntity(it)
        }
    }

    override suspend fun getArticleRemote(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<Article> {
        val result = factory.retrieveRemoteDataStore().getTopHeadlinesNews(
            apiKey,
            country,
            category,
            sources,
            q
        )
        return result.map { mapper.mapFromEntity(it) }
    }

}