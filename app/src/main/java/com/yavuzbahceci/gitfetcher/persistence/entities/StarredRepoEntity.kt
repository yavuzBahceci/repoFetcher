package com.yavuzbahceci.gitfetcher.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starred_repo_table")
data class StarredRepoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "repo_name")
    var name: String,

    @ColumnInfo(name = "owner_name")
    var ownerName: String
)