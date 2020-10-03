package com.yavuzbahceci.gitfetcher.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.ui.Data
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.Response
import com.yavuzbahceci.gitfetcher.ui.ResponseType
import com.yavuzbahceci.gitfetcher.ui.ResponseType.*
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import com.yavuzbahceci.gitfetcher.util.ApiEmptyResponse
import com.yavuzbahceci.gitfetcher.util.ApiErrorResponse
import com.yavuzbahceci.gitfetcher.util.ApiSuccessResponse
import com.yavuzbahceci.gitfetcher.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.yavuzbahceci.gitfetcher.util.GenericApiResponse
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val repoFetcherMainService: RepoFetcherMainService
){

    fun testGetRepos(ownerName: String): LiveData<DataState<MainViewState>>
    {
        return repoFetcherMainService.getUserRepos(ownerName)
            .switchMap { response ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()

                        when(response) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(
                                        null
                                    ),
                                    response = null
                                )
                            }

                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = Dialog()
                                    )
                                )
                            }

                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }

}