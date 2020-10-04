package com.yavuzbahceci.gitfetcher.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "repository_table")
data class RepositoryEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "open_issues_count")
    var openIssuesCount: Int?,

    @ColumnInfo(name = "star_count")
    var stargazerCount: Int?,

    @ColumnInfo(name = "repo_name")
    var name: String,

    @ColumnInfo(name = "owner_name")
    var ownerName: String,

    @ColumnInfo(name = "owner_picture_url")
    var avatarUrl: String


){

    override fun toString(): String {
        return "BlogPost(id=$id, " +
                "openIssuesCount='$openIssuesCount', " +
                "name='$name', " +
                "ownerName='$ownerName', " +
                "avatarUrl='$avatarUrl')"
    }


}
