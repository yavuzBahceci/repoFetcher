package com.yavuzbahceci.gitfetcher.repository.main

import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val repoFetcherMainService: RepoFetcherMainService
){

}