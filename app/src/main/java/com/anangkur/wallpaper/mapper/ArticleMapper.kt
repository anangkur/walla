package com.anangkur.wallpaper.mapper

import com.anangkur.wallpaper.model.ArticleIntent
import com.anangkur.wallpaper.presentation.model.ArticleView

class ArticleMapper: Mapper<ArticleIntent, ArticleView> {
    override fun mapToIntent(type: ArticleView): ArticleIntent {
        return ArticleIntent(
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

    override fun mapFromIntent(type: ArticleIntent): ArticleView {
        return ArticleView(
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
}