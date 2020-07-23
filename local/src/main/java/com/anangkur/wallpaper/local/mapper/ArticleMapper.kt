package com.anangkur.wallpaper.local.mapper

import com.anangkur.wallpaper.data.model.ArticleEntity
import com.anangkur.wallpaper.local.model.ArticleCached

class ArticleMapper : Mapper<ArticleCached, ArticleEntity> {

    companion object{
        private var INSTANCE: ArticleMapper? = null
        fun getInstance() = INSTANCE ?: ArticleMapper()
    }

    override fun mapFromCached(type: ArticleCached): ArticleEntity {
        return ArticleEntity(
            id = type.id,
            title = type.title,
            author = type.author,
            category = type.category,
            content = type.content,
            description = type.description,
            publishedAt = type.publishedAt,
            url = type.url,
            urlToImage = type.urlToImage
        )
    }

    override fun mapToCached(type: ArticleEntity): ArticleCached {
        return ArticleCached(
            id = type.id,
            urlToImage = type.urlToImage,
            url = type.url,
            publishedAt = type.publishedAt,
            description = type.description,
            content = type.content,
            category = type.category,
            author = type.author,
            title = type.title
        )
    }
}