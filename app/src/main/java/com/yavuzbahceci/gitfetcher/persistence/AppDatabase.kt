package com.yavuzbahceci.gitfetcher.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yavuzbahceci.gitfetcher.persistence.daos.RepositoryDao
import com.yavuzbahceci.gitfetcher.persistence.daos.StarredRepoDao
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity
import com.yavuzbahceci.gitfetcher.persistence.entities.StarredRepoEntity

@Database(entities = [RepositoryEntity::class, StarredRepoEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getRepositoryDao(): RepositoryDao

    abstract fun getStarredRepoDao(): StarredRepoDao

    companion object {

        const val DATABASE_NAME = "app_db"
    }
}