package com.anangkur.wallpaper.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

fun <T, A> resultLiveData(
    databaseQuery: () -> T,
    networkCall: suspend () -> A,
    saveCallResult: suspend (A) -> Unit
): LiveData<com.anangkur.wallpaper.base.BaseResult<T>> =
    liveData(Dispatchers.IO) {
        emit(com.anangkur.wallpaper.base.BaseResult.loading(true))
        try {
            val responseStatus = networkCall.invoke()
            emit(com.anangkur.wallpaper.base.BaseResult.loading(false))
            saveCallResult(responseStatus)
            val source = com.anangkur.wallpaper.base.BaseResult.success(databaseQuery.invoke())
            emit(source)
        } catch (e: Exception) {
            emit(com.anangkur.wallpaper.base.BaseResult.loading(false))
            emit(com.anangkur.wallpaper.base.BaseResult.error(e.message ?: ""))
            val source = com.anangkur.wallpaper.base.BaseResult.success(databaseQuery.invoke())
            emit(source)
        }
    }