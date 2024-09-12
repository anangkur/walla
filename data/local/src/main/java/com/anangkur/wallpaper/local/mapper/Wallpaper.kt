package com.anangkur.wallpaper.local.mapper

import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.localdb.model.DatabaseEntity

fun Wallpaper.toDatabaseEntity() = DatabaseEntity(
    title = title,
    creator = creator,
    imageUrl = imageUrl,
    id = id,
    isSaved = isSaved,
    thumbnailUrl = thumbnailUrl
)

fun DatabaseEntity.toWallpaper() = Wallpaper(
    id = id,
    title = title,
    imageUrl = imageUrl,
    creator = creator,
    isSaved = isSaved,
    thumbnailUrl = thumbnailUrl
)