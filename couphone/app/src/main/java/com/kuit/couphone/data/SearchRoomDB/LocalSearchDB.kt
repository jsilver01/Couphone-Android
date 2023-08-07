package com.kuit.couphone.data.SearchRoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalSearchEntity::class], version = 4)
abstract class LocalSearchDB : RoomDatabase() {
    abstract fun SearchKeywordDAO() : LocalSearchDAO

    companion object {
        private const val dbName ="localDB"
        private var instance : LocalSearchDB? = null
        @Synchronized
        fun getInstance(context: Context): LocalSearchDB?{
            if(instance == null){
                synchronized(LocalSearchDB :: class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalSearchDB::class.java,
                        "user-database"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries().build()
                }

            }
            return instance
        }
    }
}