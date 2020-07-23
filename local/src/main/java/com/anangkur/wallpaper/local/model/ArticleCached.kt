package com.anangkur.wallpaper.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleCached(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "author")
    val author: String? = "",

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "description")
    val description: String? = "",

    @ColumnInfo(name = "url")
    val url: String? = "",

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String? = "",

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String? = "",

    @ColumnInfo(name = "content")
    val content: String? = "",

    @ColumnInfo(name = "category")
    val category: String? = ""

)