package com.anangkur.wallpaper.local

import android.content.Context
import android.content.SharedPreferences
import com.anangkur.wallpaper.data.model.ArticleEntity
import com.anangkur.wallpaper.data.repository.ArticleLocal
import com.anangkur.wallpaper.local.db.AppDatabase
import com.anangkur.wallpaper.local.mapper.ArticleMapper

class LocalRepository(
    private val preferences: SharedPreferences,
    private val mapper: ArticleMapper,
    private val appDatabase: AppDatabase
): ArticleLocal {

    companion object{
        private var INSTANCE: LocalRepository? = null
        fun getInstance(context: Context) = INSTANCE ?: LocalRepository(
            context.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE),
            ArticleMapper.getInstance(),
            AppDatabase.getDatabase(context)
        )
    }

    private val expirationTime = (60 * 10 * 1000).toLong()

    override suspend fun insertData(data: List<ArticleEntity>) { data.forEach { appDatabase.getDao().insertData(mapper.mapToCached(it)) } }

    override suspend fun deleteByCategory(category: String) { appDatabase.getDao().deleteByCategory(category) }

    override fun getAllDataByCategory(category: String): List<ArticleEntity> {
        return appDatabase.getDao().getAllDataByCategory(category).map { mapper.mapFromCached(it) }
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