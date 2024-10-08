package com.anangkur.wallpaper.localdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anangkur.wallpaper.localdb.dao.AppDao
import com.anangkur.wallpaper.localdb.Const
import com.anangkur.wallpaper.localdb.model.DatabaseEntity

@Database(entities = [DatabaseEntity::class], version = 6)
abstract class AppDatabase: RoomDatabase(){

    abstract fun getDao(): AppDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null){
                synchronized(this){
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            Const.DATABASE_NAME
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                    return INSTANCE!!
                }
            }
            return INSTANCE!!
        }
    }
}