package com.anangkur.wallpaper.presentation.features.saved

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.data.model.Wallpaper
import kotlinx.coroutines.launch

class SavedViewModel(private val repository: Repository): ViewModel() {

    private val _wallpapers = MutableLiveData<List<Wallpaper>>()
    val wallpapers = _wallpapers

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading

    private val _error = MutableLiveData<String>()
    val error = _error

    fun retrieveWallpaper() {
        viewModelScope.launch {
            _loading.postValue(true)
            repository.retrieveWallpapers().runCatching {
                _loading.postValue(false)
                _wallpapers.postValue(this)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }

    fun insertWallpaper(wallpaper: Wallpaper) {
        viewModelScope.launch {
            _loading.postValue(true)
            repository.insertWallpaper(wallpaper).runCatching {
                _loading.postValue(false)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }
}