package com.anangkur.wallpaper.presentation.mapper

interface Mapper<VIEW, T> {
    fun mapToView(type: T): VIEW
}