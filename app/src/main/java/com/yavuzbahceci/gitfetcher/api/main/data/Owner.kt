package com.yavuzbahceci.gitfetcher.api.main.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {

    @SerializedName("login")
    @Expose
    var ownerName: String? = null

    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null
}