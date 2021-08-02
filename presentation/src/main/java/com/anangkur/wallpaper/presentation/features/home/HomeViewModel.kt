package com.anangkur.wallpaper.presentation.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.presentation.resultLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {

    fun fetchWallpaper(clientId: String) = resultLiveData(
        databaseQuery = { repository.retrieveWallpapers() },
        networkCall = { repository.fetchWallpapers(clientId) },
        saveCallResult = { data -> data.forEach { repository.insertWallpaper(it, false) } }
    )

    private val _collections = MutableLiveData<List<Collection>>()
    val collections: LiveData<List<Collection>> = _collections

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchCollections(clientId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            repository.fetchCollection(clientId).runCatching {
                _collections.postValue(this)
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
            repository.fetchCollections(clientId, page, perPage).runCatching {
                _otherCollections.postValue(this)
            }.onSuccess {
                _loading.postValue(false)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it.message)
            }
        }
    }
}