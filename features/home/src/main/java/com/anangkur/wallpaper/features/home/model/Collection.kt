package com.anangkur.wallpaper.features.home.model

data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val wallpapers: List<WallpaperUiModel>
)
