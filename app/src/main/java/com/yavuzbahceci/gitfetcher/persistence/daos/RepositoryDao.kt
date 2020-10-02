package com.yavuzbahceci.gitfetcher.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(repositoryEntity: RepositoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(repositoryEntity: RepositoryEntity): Long

    @Query("SELECT * FROM repository_table WHERE id = :id")
    fun searchById(id: Int): RepositoryEntity

    @Query("SELECT * FROM repository_table WHERE repo_name = :name")
    fun searchByName(name: String): RepositoryEntity

    @Query("SELECT * FROM repository_table WHERE owner_name = :ownerName")
    fun searchByOwnerName(ownerName: String): List<RepositoryEntity>

}