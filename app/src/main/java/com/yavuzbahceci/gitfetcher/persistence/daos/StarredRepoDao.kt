package com.yavuzbahceci.gitfetcher.persistence.daos

import androidx.room.*
import com.yavuzbahceci.gitfetcher.persistence.entities.StarredRepoEntity

@Dao
interface StarredRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(starredRepoEntity: StarredRepoEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(starredRepoEntity: StarredRepoEntity): Long

    @Query("SELECT * FROM starred_repo_table WHERE id = :id")
    fun searchById(id: Int): StarredRepoEntity

    @Query("SELECT * FROM starred_repo_table WHERE repo_name = :name")
    fun searchByName(name: String): StarredRepoEntity

    @Query("SELECT * FROM starred_repo_table WHERE owner_name = :ownerName")
    fun searchByOwnerName(ownerName: String): List<StarredRepoEntity>

    @Delete
    fun delete(starredRepoEntity: StarredRepoEntity)
}