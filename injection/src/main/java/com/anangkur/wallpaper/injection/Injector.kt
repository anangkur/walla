package com.anangkur.wallpaper.injection

import android.content.Context
import com.anangkur.wallpaper.data.Repository
import com.anangkur.wallpaper.local.LocalRepository
import com.anangkur.wallpaper.remote.RemoteRepository

object Injector {

    fun provideViewModelFactory(context: Context) = ViewModelFactory.getInstance(provideRepository(context))

    private fun provideRepository(context: Context) = Repository.getInstance(
        localRepository = provideLocalRepository(context),
        remoteRepository = provideRemoteRepository()
    )

    private fun provideLocalRepository(context: Context) = LocalRepository.getInstance(context)

    private fun provideRemoteRepository() = RemoteRepository.getInstance()


}