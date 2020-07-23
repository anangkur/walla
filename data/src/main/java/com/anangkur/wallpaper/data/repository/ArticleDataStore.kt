package com.anangkur.wallpaper.data.repository

import com.anangkur.wallpaper.data.model.ArticleEntity

interface ArticleDataStore {
    suspend fun insertData(data: List<ArticleEntity>)
    suspend fun deleteByCategory(category: String)
    fun getAllDataByCategory(category: String): List<ArticleEntity>
    suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<ArticleEntity>
}