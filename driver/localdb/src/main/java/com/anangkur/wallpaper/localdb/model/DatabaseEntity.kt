package com.anangkur.wallpaper.localdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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