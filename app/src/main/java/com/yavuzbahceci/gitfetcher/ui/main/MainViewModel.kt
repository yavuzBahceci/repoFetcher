package com.yavuzbahceci.gitfetcher.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.repository.main.MainRepository
import com.yavuzbahceci.gitfetcher.util.GenericApiResponse
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
        val mainRepository: MainRepository
    ): ViewModel() {


    fun testRetrofit(): LiveData<GenericApiResponse<List<RepositoryResponse>>> {
        return mainRepository.testGetRepos("yavuzBahceci")
    }
}