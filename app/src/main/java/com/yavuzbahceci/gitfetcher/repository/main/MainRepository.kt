package com.yavuzbahceci.gitfetcher.repository.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.persistence.daos.RepositoryDao
import com.yavuzbahceci.gitfetcher.persistence.daos.StarredRepoDao
import com.yavuzbahceci.gitfetcher.repository.NetworkBoundResource
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.Response
import com.yavuzbahceci.gitfetcher.ui.ResponseType
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import com.yavuzbahceci.gitfetcher.ui.main.state.SearchField
import com.yavuzbahceci.gitfetcher.util.*
import com.yavuzbahceci.gitfetcher.util.ErrorHandling.Companion.EMPTY_RESULT
import com.yavuzbahceci.gitfetcher.util.SuccessHandling.Companion.SUCCESS_GENERAL
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val starredRepoDao: StarredRepoDao,
    val repositoryDao: RepositoryDao,
    val repoFetcherMainService: RepoFetcherMainService,
    val internetChecker: InternetChecker,
    val preferences: SharedPreferences,
    val editor: SharedPreferences.Editor
) {

    private var repositoryJob: Job? = null



    fun attemptSearch(ownerName: String): LiveData<DataState<MainViewState>> {

        val searchFieldErrors = SearchField(ownerName).isValidForSearch()
        if (!searchFieldErrors.equals(SearchField.SearchError.none())) {
            return returnErrorResponse(searchFieldErrors, ResponseType.Dialog())
        }

        return object : NetworkBoundResource<List<RepositoryResponse>, MainViewState>(
            internetChecker.isConnectedToTheInternet(),
            true
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

                response.body.map {

                }



                onCompleteJob(
                    DataState.data(
                        data = MainViewState(
                            // set list
                        )
                    )
                )
            }

            override suspend fun createCacheRequestAndReturn() {
                TODO("Not yet implemented")
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

    private fun saveIdToFavoriteList(id: Int): Long {
        val list = preferences.getString(PreferenceKeys.FAV_LIST, null)?.toMutableList()
        list?.add(id.toChar())
        editor.putString(PreferenceKeys.FAV_LIST,list.toString())
        return -1
    }

    fun checkPreviousFavoriteList(): LiveData<DataState<MainViewState>> {
        val previousFavList = preferences.getString(PreferenceKeys.FAV_LIST, null)?.toMutableList()
        if (previousFavList.isNullOrEmpty()) {
            Log.d(TAG, "checkPreviousFavoriteList: No previous choice for favorite")
            return noPreviousListFound()
        }
        return object: NetworkBoundResource<Void, MainViewState>(
            internetChecker.isConnectedToTheInternet(),
            false
        ){
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Void>) {

            }

            override fun createCall(): LiveData<GenericApiResponse<Void>> {
                return AbsentLiveData.create()
            }

            override suspend fun createCacheRequestAndReturn() {

            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

        }.asLiveData()
    }

    private fun noPreviousListFound(): LiveData<DataState<MainViewState>> {

        return object: LiveData<DataState<MainViewState>>(){
            override fun onActive() {
                super.onActive()
                value = DataState.data(
                    data = null,
                    response = Response(SUCCESS_GENERAL, ResponseType.None())
                )
            }
        }
    }


    companion object {
        private const val TAG = "MainRepository"
    }

}