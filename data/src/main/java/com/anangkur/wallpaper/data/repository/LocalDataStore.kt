package com.anangkur.wallpaper.data.repository

interface LocalDataStore {
    fun isExpired(): Boolean
}