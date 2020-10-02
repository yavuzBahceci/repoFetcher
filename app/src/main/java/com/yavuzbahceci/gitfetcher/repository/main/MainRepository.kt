package com.yavuzbahceci.gitfetcher.repository.main

import androidx.lifecycle.LiveData
import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.util.GenericApiResponse
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val repoFetcherMainService: RepoFetcherMainService
){

    fun testGetRepos(ownerName: String): LiveData<GenericApiResponse<List<RepositoryResponse>>>
    {
        return repoFetcherMainService.getUserRepos(ownerName)
    }

}