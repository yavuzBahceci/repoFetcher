package com.yavuzbahceci.gitfetcher.models

data class Repository(
    val id: Int,
    val repoName: String,
    val ownerName: String,
    val avatarUrl: String,
    val starCount: Int,
    val openIssueCount: Int
)

enum class StarState(val type: Int) {
    NOT_STARRED(0),
    STARRED(1)
}