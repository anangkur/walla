package com.anangkur.wallpaper.remote.mapper

interface Mapper<in MODEL, out T> {
    fun mapFromRemote(type: MODEL): T
}