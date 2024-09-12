package com.anangkur.wallpaper.features.saved.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.wallpaper.domain.repository.Repository
import com.anangkur.wallpaper.features.saved.SavedViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T  =
        with(modelClass) {
            when {
                isAssignableFrom(SavedViewModel::class.java) -> SavedViewModel(
                    repository = repository
                )
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object{
        @Volatile private var INSTANCE: ViewModelFactory? = null
        fun getInstance(
            repository: Repository,
        ): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(repository = repository).also { INSTANCE = it }
            }
        }
    }
}