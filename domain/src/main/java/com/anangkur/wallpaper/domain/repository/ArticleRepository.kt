package com.anangkur.wallpaper.domain.repository

import com.anangkur.wallpaper.domain.model.Article

interface ArticleRepository {
    suspend fun clearArticle(category: String)
    suspend fun saveArticles(articles: List<Article>)
    fun getArticlesLocal(category: String): List<Article>
    suspend fun getArticleRemote(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<Article>
}