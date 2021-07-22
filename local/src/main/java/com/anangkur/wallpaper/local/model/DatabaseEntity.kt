package com.anangkur.wallpaper.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseEntity(
    @PrimaryKey
    val id: String
)