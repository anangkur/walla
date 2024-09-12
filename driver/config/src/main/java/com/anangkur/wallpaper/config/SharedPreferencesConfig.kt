package com.anangkur.wallpaper.config

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesConfig(context: Context) {

    companion object {
        private const val PREF_NAME = "PREF_NAME"
        private const val PREF_CACHED_TIME = "PREF_CACHED_TIME"
        private var INSTANCE: SharedPreferencesConfig? = null
        fun getInstance(context: Context) = INSTANCE ?: SharedPreferencesConfig(context)
    }

    private val instance: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveCacheTime(cacheTime: Long) {
        instance.edit().putLong(PREF_CACHED_TIME, cacheTime).apply()
    }

    fun loadCacheTime(): Long {
        return instance.getLong(PREF_CACHED_TIME, 0)
    }
}