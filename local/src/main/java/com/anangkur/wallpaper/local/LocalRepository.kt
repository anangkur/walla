package com.anangkur.wallpaper.local

import android.content.Context
import android.content.SharedPreferences
import com.anangkur.wallpaper.data.repository.LocalDataStore
import com.anangkur.wallpaper.local.db.AppDatabase

class LocalRepository(
    private val preferences: SharedPreferences,
    private val appDatabase: AppDatabase
): LocalDataStore {

    companion object{
        private var INSTANCE: LocalRepository? = null
        fun getInstance(context: Context) = INSTANCE ?: LocalRepository(
            context.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE),
            AppDatabase.getDatabase(context)
        )
    }

    private val expirationTime = (60 * 10 * 1000).toLong()

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > expirationTime
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferences.getLong(Const.PREF_CACHED_TIME, 0)
    }
}