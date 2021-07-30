package com.anangkur.wallpaper.remote.model.unsplash


import com.anangkur.wallpaper.data.model.Collection
import com.google.gson.annotations.SerializedName

data class CollectionResponse(
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_collected_at")
    val lastCollectedAt: String?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("private")
    val `private`: Boolean?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("share_key")
    val shareKey: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("total_photos")
    val totalPhotos: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("preview_photos")
    val previewPhotos: List<PreviewPhotos>
)

fun CollectionResponse.toCollection() = Collection(
    id = id.toString(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    wallpapers = previewPhotos.map { it.urls.regular.orEmpty() }
)