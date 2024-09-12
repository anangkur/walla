package com.anangkur.wallpaper.local.mapper

interface Mapper<CACHED, T> {
    fun mapFromCached(type: CACHED): T
    fun mapToCached(type: T): CACHED
}