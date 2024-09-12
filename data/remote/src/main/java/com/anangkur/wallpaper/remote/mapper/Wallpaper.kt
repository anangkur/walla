package com.anangkur.wallpaper.remote.mapper

import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.rest.model.unsplash.WallpaperResponse

fun WallpaperResponse.toWallpaper() = Wallpaper(
    id = id.orEmpty(),
    creator = user?.name.orEmpty().ifEmpty { "-" },
    imageUrl = urls?.full.orEmpty(),
    isSaved = false,
    title = description.orEmpty().ifEmpty { altDescription.orEmpty().ifEmpty { "-" } },
    thumbnailUrl = urls?.regular.orEmpty()
)