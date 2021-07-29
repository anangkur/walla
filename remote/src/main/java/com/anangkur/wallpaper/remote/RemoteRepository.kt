package com.anangkur.wallpaper.remote

import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.data.repository.RemoteRepository
import com.anangkur.wallpaper.remote.model.unsplash.toWallpaper
import com.anangkur.wallpaper.remote.services.UnsplashService

class RemoteRepository(private val unsplashService: UnsplashService): RemoteRepository {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance(unsplashService: UnsplashService) = INSTANCE ?: RemoteRepository(unsplashService)
    }

    override suspend fun fetchWallpaper(clientId: String): List<Wallpaper> {
        return unsplashService.getPhotos(clientId).map { it.toWallpaper() }
    }

    override suspend fun fetchCollection(): List<Collection> {
        val items = ArrayList<Collection>()
        val subItems = ArrayList<Wallpaper>()
        for (i in 1..10) {
            subItems.add(
                Wallpaper(
                    id = i.toString(),
                    title = "Creation shel $i",
                    imageUrl = "https://picsum.photos/1080/1920",
                    creator = "by Fallout legacy",
                    isSaved = false,
                    thumbnailUrl = "https://picsum.photos/1080/1920"
                )
            )
        }
        for (i in 1..10) {
            items.add(
                Collection(
                    id = i.toString(),
                    title = "Amoled Club $i",
                    description = "A common pitch black wallpaper",
                    imageUrl = "https://picsum.photos/1080/1920",
                    wallpapers = subItems
                )
            )
        }
        return items
    }
}