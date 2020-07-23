package com.anangkur.wallpaper.data.mapper

interface Mapper<ENTITY, T> {
    fun mapToEntity(type: T): ENTITY
    fun mapFromEntity(type: ENTITY): T
}