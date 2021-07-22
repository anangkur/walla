package com.anangkur.wallpaper.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.anangkur.wallpaper.model.BaseResult
import kotlinx.coroutines.Dispatchers

fun <T, A> resultLiveData(
    databaseQuery: () -> T,
    networkCall: suspend () -> A,
    saveCallResult: suspend (A) -> Unit
): LiveData<BaseResult<T>> =
    liveData(Dispatchers.IO) {
        emit(BaseResult.loading(true))
        try {
            val responseStatus = networkCall.invoke()
            emit(BaseResult.loading(false))
            saveCallResult(responseStatus)
            val source = BaseResult.success(databaseQuery.invoke())
            emit(source)
        } catch (e: Exception) {
            emit(BaseResult.loading(false))
            emit(BaseResult.error(e.message ?: ""))
            val source = BaseResult.success(databaseQuery.invoke())
            emit(source)
        }
    }