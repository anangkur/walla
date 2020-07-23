package com.anangkur.wallpaper.remote

import com.anangkur.wallpaper.data.model.ArticleEntity
import com.anangkur.wallpaper.data.repository.ArticleRemote
import com.anangkur.wallpaper.remote.mapper.ArticleMapper

class RemoteRepository (
    private val mapper: ArticleMapper,
    private val service: ApiService
): ArticleRemote {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository(
            ArticleMapper.getInstance(),
            ApiService.getApiService
        )
    }

    override suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): List<ArticleEntity> {
        val response = service.getTopHeadlinesNews(
            apiKey,
            country,
            category,
            sources,
            q
        )
        return if (response.status == "ok"){
            val data = response.articleEntities.map { mapper.mapFromRemote(it) }
            data
        } else {
            emptyList()
        }
    }
}