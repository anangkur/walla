package com.anangkur.wallpaper.presentation.mapper

import com.anangkur.wallpaper.domain.model.Article
import com.anangkur.wallpaper.presentation.model.ArticleView

class ArticleMapper: Mapper<ArticleView, Article> {

    companion object{
        private var INSTANCE: ArticleMapper? = null
        fun getInstance() = INSTANCE ?: ArticleMapper()
    }

    override fun mapToView(type: Article): ArticleView {
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