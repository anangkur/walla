package com.anangkur.wallpaper.local.model

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anangkur.wallpaper.data.model.Wallpaper

@Entity
data class DatabaseEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "creator")
    val creator: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "isSaved")
    val isSaved: Boolean,

    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String
)

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