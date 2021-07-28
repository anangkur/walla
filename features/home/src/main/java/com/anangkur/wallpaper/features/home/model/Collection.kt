package com.anangkur.wallpaper.features.home.model

import com.anangkur.wallpaper.data.model.Wallpaper

data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val wallpapers: List<Wallpaper>
)
