package com.anangkur.wallpaper.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.wallpaper.domain.model.Wallpaper
import com.anangkur.wallpaper.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchResultViewModel(private val repository: Repository): ViewModel() {

    private val _searchResult = MutableLiveData<List<Wallpaper>>()
    val searchResult: LiveData<List<Wallpaper>> = _searchResult

    private val _loadingSearchResult = MutableLiveData<Boolean>()
    val loadingSearchResult: LiveData<Boolean> = _loadingSearchResult

    private val _errorSearchResult = MutableLiveData<String>()
    val errorSearchResult: LiveData<String> = _errorSearchResult

    fun searchWallpaper(clientId: String, query: String? = null, color: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingSearchResult.postValue(true)
            runCatching {
                _searchResult.postValue(
                    if (query != null) repository.fetchSearchByQuery(clientId, query)
                    else if (color != null) repository.fetchSearchByColor(clientId, color)
                    else emptyList()
                )
            }.onSuccess {
                _loadingSearchResult.postValue(false)
            }.onFailure {
                _loadingSearchResult.postValue(false)
                _errorSearchResult.postValue(it.message)
            }
        }
    }
}