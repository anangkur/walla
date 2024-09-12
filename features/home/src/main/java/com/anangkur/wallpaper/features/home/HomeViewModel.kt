package com.anangkur.wallpaper.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.anangkur.wallpaper.domain.model.Collection as ModelCollection

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

    private val _collections = MutableLiveData<List<ModelCollection>>()
    val collections: LiveData<List<ModelCollection>> = _collections

    private val _loadingCollections = MutableLiveData<Boolean>()
    val loadingCollections: LiveData<Boolean> = _loadingCollections

    private val _errorCollections = MutableLiveData<String>()
    val errorCollections: LiveData<String> = _errorCollections

    fun fetchCollections(clientId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingCollections.postValue(true)
            runCatching {
                _collections.postValue(repository.fetchCollection(clientId))
            }.onSuccess {
                _loadingCollections.postValue(false)
            }.onFailure {
                _loadingCollections.postValue(false)
                _errorCollections.postValue(it.message)
            }
        }
    }

    private val _otherCollections = MutableLiveData<List<ModelCollection>>()
    val otherCollections: LiveData<List<ModelCollection>> = _otherCollections

    private val _loadingOtherCollections = MutableLiveData<Boolean>()
    val loadingOtherCollections: LiveData<Boolean> = _loadingOtherCollections

    private val _errorOtherCollections = MutableLiveData<String>()
    val errorOtherCollections: LiveData<String> = _errorOtherCollections

    fun fetchCollections(clientId: String, page: Int, perPage: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingOtherCollections.postValue(true)
            runCatching {
                _otherCollections.postValue(repository.fetchCollections(clientId, page, perPage))
            }.onSuccess {
                _loadingOtherCollections.postValue(false)
            }.onFailure {
                _loadingOtherCollections.postValue(false)
                _errorOtherCollections.postValue(it.message)
            }
        }
    }
}