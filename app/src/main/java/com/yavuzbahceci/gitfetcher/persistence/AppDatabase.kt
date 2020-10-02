package com.yavuzbahceci.gitfetcher.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yavuzbahceci.gitfetcher.persistence.daos.RepositoryDao
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getRepositoryDao(): RepositoryDao

    companion object {

        const val DATABASE_NAME = "app_db"
    }
}