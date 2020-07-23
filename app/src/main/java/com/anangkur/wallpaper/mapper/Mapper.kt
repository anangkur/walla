package com.anangkur.wallpaper.mapper

interface Mapper<INTENT, T> {
    fun mapToIntent(type: T): INTENT
    fun mapFromIntent(type: INTENT): T
}