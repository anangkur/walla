package com.anangkur.wallpaper.domain.model

data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val wallpapers: List<String>
)
