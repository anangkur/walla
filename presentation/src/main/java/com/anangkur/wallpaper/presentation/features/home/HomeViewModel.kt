package com.anangkur.wallpaper.presentation.features.home

import androidx.lifecycle.ViewModel
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.presentation.resultLiveData

class HomeViewModel(private val repository: Repository): ViewModel() {

    fun fetchWallpaper() = resultLiveData(
        databaseQuery = { repository.retrieveWallpapers() },
        networkCall = { repository.fetchWallpapers() },
        saveCallResult = { data -> data.forEach { repository.insertWallpaper(it) } }
    )
}