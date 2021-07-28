package com.anangkur.wallpaper.local

import android.content.Context
import android.content.SharedPreferences
import com.anangkur.wallpaper.data.model.Wallpaper
import com.anangkur.wallpaper.data.repository.LocalRepository
import com.anangkur.wallpaper.local.db.AppDatabase
import com.anangkur.wallpaper.local.model.toDatabaseEntity
import com.anangkur.wallpaper.local.model.toWallpaper

class LocalRepository(
    private val preferences: SharedPreferences,
    private val appDatabase: AppDatabase
): LocalRepository {

    companion object{
        private var INSTANCE: LocalRepository? = null
        fun getInstance(context: Context) = INSTANCE ?: LocalRepository(
            context.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE),
            AppDatabase.getDatabase(context)
        )
    }

    private val expirationTime = (60 * 10 * 1000).toLong()

    override suspend fun insertWallpaper(wallpaper: Wallpaper) {
        appDatabase.getDao().insertWallpaper(wallpaper = wallpaper.toDatabaseEntity())
    }

    override suspend fun retrieveWallpapers(): List<Wallpaper> {
        return appDatabase.getDao().loadAllWallpaper().map { it.toWallpaper() }
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > expirationTime
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferences.getLong(Const.PREF_CACHED_TIME, 0)
    }
}