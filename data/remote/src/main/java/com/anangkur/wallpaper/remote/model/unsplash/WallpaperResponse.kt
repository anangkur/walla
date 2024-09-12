package com.anangkur.wallpaper.remote.model.unsplash

import com.anangkur.wallpaper.domain.model.Wallpaper
import com.google.gson.annotations.SerializedName

data class WallpaperResponse(
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("blur_hash")
    val blurHash: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("urls")
    val urls: Urls?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("width")
    val width: Int?
)

fun WallpaperResponse.toWallpaper() = Wallpaper(
    id = id.orEmpty(),
    creator = user?.name.orEmpty().ifEmpty { "-" },
    imageUrl = urls?.full.orEmpty(),
    isSaved = false,
    title = description.orEmpty().ifEmpty { altDescription.orEmpty().ifEmpty { "-" } },
    thumbnailUrl = urls?.regular.orEmpty()
)