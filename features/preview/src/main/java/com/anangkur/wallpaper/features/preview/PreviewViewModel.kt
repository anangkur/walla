package com.anangkur.wallpaper.features.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreviewViewModel(private val repository: Repository): ViewModel() {

    companion object {
        sealed class Action {
            object Delete: Action()
            object Insert: Action()
        }
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _success = MutableLiveData<Action>()
    val success: LiveData<Action> = _success

    fun insertWallpaper(wallpaper: Wallpaper) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            repository.insertWallpaper(wallpaper.copy(isSaved = true), true).runCatching {
                _loading.postValue(false)
                _success.postValue(Action.Insert)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }

    fun deleteWallpaper(wallpaper: Wallpaper) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            repository.insertWallpaper(wallpaper.copy(isSaved = false), true).runCatching {
                _loading.postValue(false)
                _success.postValue(Action.Insert)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }
}