package com.anangkur.wallpaper.data.model

data class ArticleEntity(
    val id: Int = 0,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = "",
    val category: String? = ""
)