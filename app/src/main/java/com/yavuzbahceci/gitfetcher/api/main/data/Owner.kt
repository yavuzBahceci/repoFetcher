package com.yavuzbahceci.gitfetcher.api.main.data

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login") var ownerName: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
)