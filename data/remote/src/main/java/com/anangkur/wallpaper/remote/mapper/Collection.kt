package com.anangkur.wallpaper.remote.mapper

import com.anangkur.wallpaper.domain.model.Collection
import com.anangkur.wallpaper.rest.model.unsplash.CollectionResponse

fun CollectionResponse.toCollection() = Collection(
    id = id.orEmpty(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    wallpapers = previewPhotos.map { it.urls.regular.orEmpty() }
)