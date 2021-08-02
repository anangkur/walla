package com.anangkur.wallpaper.presentation.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.presentation.model.BaseResult
import com.anangkur.wallpaper.presentation.resultLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {

    private val _suggestions = MutableLiveData<List<Wallpaper>>()
    val suggestions: LiveData<List<Wallpaper>> = _suggestions

    private val _loadingSuggestions = MutableLiveData<Boolean>()
    val loadingSuggestions: LiveData<Boolean> = _loadingSuggestions

    private val _errorSuggestions = MutableLiveData<String>()
    val errorSuggestions: LiveData<String> = _errorSuggestions

    fun fetchWallpaper(clientId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingSuggestions.postValue(true)
            runCatching {
                repository.fetchWallpapers(clientId).forEach { repository.insertWallpaper(it, false) }
                _suggestions.postValue(repository.retrieveWallpapers())
            }.onSuccess {
                _loadingSuggestions.postValue(false)
            }.onFailure {
                _loadingSuggestions.postValue(false)
                _errorSuggestions.postValue(it.message)
            }
        }
    }

    private val _collections = MutableLiveData<List<Collection>>()
    val collections: LiveData<List<Collection>> = _collections

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchCollections(clientId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            runCatching {
                _collections.postValue(repository.fetchCollection(clientId))
            }.onSuccess {
                _loading.postValue(false)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }

    private val _otherCollections = MutableLiveData<List<Collection>>()
    val otherCollections: LiveData<List<Collection>> = _otherCollections

    fun fetchCollections(clientId: String, page: Int, perPage: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            runCatching {
                _otherCollections.postValue(repository.fetchCollections(clientId, page, perPage))
            }.onSuccess {
                _loading.postValue(false)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }
}