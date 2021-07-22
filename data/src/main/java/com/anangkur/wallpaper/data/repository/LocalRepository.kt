package com.anangkur.wallpaper.data.repository

interface LocalRepository {
    fun isExpired(): Boolean
}