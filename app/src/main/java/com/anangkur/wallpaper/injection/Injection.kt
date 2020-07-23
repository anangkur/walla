package com.anangkur.wallpaper.injection

import android.content.Context
import com.anangkur.wallpaper.data.ArticleDataRepository
import com.anangkur.wallpaper.presentation.GetArticles
import com.anangkur.wallpaper.local.LocalRepository
import com.anangkur.wallpaper.remote.RemoteRepository

object Injection{
    fun provideGetArticle(context: Context) = GetArticles.getInstance(
        ArticleDataRepository.getInstance(
            LocalRepository.getInstance(context), RemoteRepository.getInstance()
        ))
}