package com.cleanarchitectureexample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cleanarchitectureexample.dao.PostDao
import com.cleanarchitectureexample.domain.model.PostResponse

@Database(entities = [PostResponse::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}