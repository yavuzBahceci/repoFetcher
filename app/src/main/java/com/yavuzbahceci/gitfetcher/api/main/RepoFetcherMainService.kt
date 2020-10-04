package com.yavuzbahceci.gitfetcher.api.main

import androidx.lifecycle.LiveData
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoFetcherMainService {

    @GET(getUserReposUrl)
    fun getUserRepos(
        @Path(ownerArgName) ownerName: String,
        @Query(pageArgName) page: Int
    ) :LiveData<GenericApiResponse<List<RepositoryResponse>>>

    companion object {

        private const val pageArgName = "page"
        private const val ownerArgName = "userName"
        private const val getUserReposUrl = "users/{$ownerArgName}/repos"
    }

}