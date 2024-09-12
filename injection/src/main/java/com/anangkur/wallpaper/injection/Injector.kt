package com.anangkur.wallpaper.injection

import android.content.Context
import com.anangkur.wallpaper.domain.repository.Repository
import com.anangkur.wallpaper.local.LocalRepository
import com.anangkur.wallpaper.remote.RemoteRepository
import com.anangkur.wallpaper.data.Repository.Companion as RepositoryImpl

class Injector(
    private val context: Context,
    private val baseUrl: String,
) {

    val repository: Repository by lazy {
        RepositoryImpl.getInstance(
            localRepository = provideLocalRepository(context),
            remoteRepository = provideRemoteRepository(baseUrl)
        )
    }

    private fun provideLocalRepository(context: Context) = LocalRepository.getInstance(context)

    private fun provideRemoteRepository(baseUrl: String) = RemoteRepository.getInstance(baseUrl)
}