package com.anangkur.wallpaper.presentation.features.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.data.model.Wallpaper
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
            repository.insertWallpaper(wallpaper).runCatching {
                _loading.postValue(false)
                _success.postValue(Action.Insert)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }

    fun deleteWallpaper(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            repository.deleteWallpaper(id).runCatching {
                _loading.postValue(false)
                _success.postValue(Action.Delete)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }
}