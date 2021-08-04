package com.anangkur.wallpaper.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.presentation.features.collection.CollectionViewModel
import com.anangkur.wallpaper.presentation.features.home.HomeViewModel
import com.anangkur.wallpaper.presentation.features.preview.PreviewViewModel
import com.anangkur.wallpaper.presentation.features.saved.SavedViewModel
import com.anangkur.wallpaper.presentation.features.search.SearchResultViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T  =
        with(modelClass) {
            when {
                isAssignableFrom(SavedViewModel::class.java) -> SavedViewModel(repository = repository)
                isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository = repository)
                isAssignableFrom(PreviewViewModel::class.java) -> PreviewViewModel(repository = repository)
                isAssignableFrom(CollectionViewModel::class.java) -> CollectionViewModel(repository = repository)
                isAssignableFrom(SearchResultViewModel::class.java) -> SearchResultViewModel(repository = repository)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object{
        @Volatile private var INSTANCE: ViewModelFactory? = null
        fun getInstance(repository: Repository) = INSTANCE ?: synchronized(ViewModelFactory::class.java) {
            INSTANCE ?: ViewModelFactory(repository = repository).also { INSTANCE = it }
        }
    }
}