package com.anangkur.wallpaper.remote.model.unsplash

import com.google.gson.annotations.SerializedName

data class PreviewPhotos(
    @SerializedName("id")
    val id: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("blur_hash")
    val blurHash: String?,
    @SerializedName("urls")
    val urls: Urls
)
