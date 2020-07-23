package com.anangkur.wallpaper.data.repository

import com.anangkur.wallpaper.data.model.ArticleEntity

interface ArticleRemote {
    suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<ArticleEntity>
}