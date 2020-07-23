package com.anangkur.wallpaper.remote.model.news

import com.anangkur.wallpaper.remote.model.news.ArticleModel
import com.google.gson.annotations.SerializedName

data class GetNewsResponse(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val articleEntities: List<ArticleModel> = listOf(),
    @SerializedName("message")
    val message: String? = ""
)