package com.yavuzbahceci.gitfetcher.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.repository.NetworkBoundResource
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.Response
import com.yavuzbahceci.gitfetcher.ui.ResponseType
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import com.yavuzbahceci.gitfetcher.ui.main.state.SearchField
import com.yavuzbahceci.gitfetcher.util.ApiSuccessResponse
import com.yavuzbahceci.gitfetcher.util.ErrorHandling.Companion.EMPTY_RESULT
import com.yavuzbahceci.gitfetcher.util.GenericApiResponse
import com.yavuzbahceci.gitfetcher.util.InternetChecker
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val repoFetcherMainService: RepoFetcherMainService,
    val internetChecker: InternetChecker
) {

    private var repositoryJob: Job? = null

    fun attemptSearch(ownerName: String): LiveData<DataState<MainViewState>> {

        val searchFieldErrors = SearchField(ownerName).isValidForSearch()
        if (!searchFieldErrors.equals(SearchField.SearchError.none())) {
            return returnErrorResponse(searchFieldErrors, ResponseType.Dialog())
        }

        return object : NetworkBoundResource<List<RepositoryResponse>, MainViewState>(
            internetChecker.isConnectedToTheInternet()
        ){

            override fun createCall(): LiveData<GenericApiResponse<List<RepositoryResponse>>> {
                return repoFetcherMainService.getUserRepos(ownerName)
            }

            override fun setJob(job: Job) {
                cancelActiveJobs()
                repositoryJob = job
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<List<RepositoryResponse>>) {
                Log.d(TAG, "handleApiSuccessResponse: $response")
                if (response.body.isEmpty()){
                    return onErrorReturn(EMPTY_RESULT, shouldDialog = true, shouldUseToast = false)
                }

                onCompleteJob(
                    DataState.data(
                        data = MainViewState(
                            // set list
                        )
                    )
                )
            }

        }.asLiveData()
    }

    private fun returnErrorResponse(errorMessage: String, responseType: ResponseType):
            LiveData<DataState<MainViewState>> {
        return object : LiveData<DataState<MainViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                    Response(
                        errorMessage,
                        responseType
                    )
                )
            }
        }
    }

    fun cancelActiveJobs(){
        Log.d(TAG, "cancelActiveJobs: Cancelling on-going jobs")
        repositoryJob?.cancel()
    }


    companion object {
        private const val TAG = "MainRepository"
    }

}