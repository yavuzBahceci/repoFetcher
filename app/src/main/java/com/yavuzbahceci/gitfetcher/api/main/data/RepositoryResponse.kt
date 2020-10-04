package com.yavuzbahceci.gitfetcher.api.main.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepositoryResponse {

    @SerializedName("id")
    @Expose
    var repositoryId: Int = -1

    @SerializedName("open_issues_count")
    @Expose
    var openIssuesCount: Int? = null

    @SerializedName("stargazers_count")
    @Expose
    var starsCount: Int? = null

    @SerializedName("name")
    @Expose
    var repositoryName: String? = null

    @SerializedName("owner")
    @Expose
    var owner: Owner? = null

}