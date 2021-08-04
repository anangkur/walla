package com.anangkur.wallpaper.remote.services

import com.anangkur.wallpaper.remote.model.unsplash.CollectionResponse
import com.anangkur.wallpaper.remote.model.unsplash.SearchResult
import com.anangkur.wallpaper.remote.model.unsplash.WallpaperResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashService {

    @GET("/photos")
    suspend fun getPhotos(@Query("client_id") clientId: String): List<WallpaperResponse>

    @GET("/collections")
    suspend fun getCollections(
        @Query("client_id") clientId: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): List<CollectionResponse>

    @GET("/collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") collectionId: String,
        @Query("client_id") clientId: String
    ): List<WallpaperResponse>

    @GET("/search/photos")
    suspend fun getSearchPhotos(
        @Query("client_id") clientId: String,
        @Query("query") query: String = "",
        @Query("color") color: String? = null
    ): SearchResult
}