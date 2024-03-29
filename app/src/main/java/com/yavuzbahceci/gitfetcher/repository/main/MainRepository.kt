package com.yavuzbahceci.gitfetcher.repository.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.persistence.daos.RepositoryDao
import com.yavuzbahceci.gitfetcher.persistence.daos.StarredRepoDao
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity
import com.yavuzbahceci.gitfetcher.persistence.entities.StarredRepoEntity
import com.yavuzbahceci.gitfetcher.repository.JobManager
import com.yavuzbahceci.gitfetcher.repository.NetworkBoundResource
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.Response
import com.yavuzbahceci.gitfetcher.ui.ResponseType
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState.ListRepoFields
import com.yavuzbahceci.gitfetcher.ui.main.state.SearchField
import com.yavuzbahceci.gitfetcher.util.ApiSuccessResponse
import com.yavuzbahceci.gitfetcher.util.Constants.Companion.PAGINATION_PAGE_SIZE
import com.yavuzbahceci.gitfetcher.util.GenericApiResponse
import com.yavuzbahceci.gitfetcher.util.InternetChecker
import com.yavuzbahceci.gitfetcher.util.SuccessHandling.Companion.SUCCESS_GENERAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val starredRepoDao: StarredRepoDao,
    val repositoryDao: RepositoryDao,
    val repoFetcherMainService: RepoFetcherMainService,
    val internetChecker: InternetChecker,
    val preferences: SharedPreferences,
    val editor: SharedPreferences.Editor,
) : JobManager("MainRepository") {

    fun attemptSearch(ownerName: String, page: Int): LiveData<DataState<MainViewState>> {

        val searchFieldErrors = SearchField(ownerName).isValidForSearch()
        if (searchFieldErrors != SearchField.SearchError.none()) {
            return returnErrorResponse(searchFieldErrors, ResponseType.Dialog())
        }

        return object :
            NetworkBoundResource<List<RepositoryResponse>, List<RepositoryEntity>, MainViewState>(
                internetChecker.isConnectedToTheInternet(),
                true,
                false,
                true
            ) {

            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        viewState.listRepoFields.isQueryInProgress = false
                        if (page * PAGINATION_PAGE_SIZE > viewState.listRepoFields.repoList.size) {
                            viewState.listRepoFields.isQueryExhausted = true
                        }
                        onCompleteJob(DataState.data(viewState, null))
                    }
                }
            }

            override fun loadFromCache(): LiveData<MainViewState> {
                return repositoryDao.getSearchedRepositories(ownerName, page).switchMap {
                    object : LiveData<MainViewState>() {
                        override fun onActive() {
                            super.onActive()
                            value = MainViewState(
                                ListRepoFields(
                                    repoList = it,
                                    isQueryInProgress = true
                                )
                            )
                        }
                    }
                }
            }

            override suspend fun updateLocalDb(cacheObject: List<RepositoryEntity>?) {
                if (cacheObject != null) {
                    withContext(IO) {
                        for (repo in cacheObject) {
                            try {
                                launch {
                                    Log.d(TAG, "updateLocalDb: Repo inserting $repo")
                                    repositoryDao.insertOrIgnore(repo)
                                }
                            } catch (e: Exception) {
                                Log.e(
                                    TAG,
                                    "updateLocalDb: Error updating repository db ${e.message}",
                                )
                            }
                        }
                    }
                }
            }

            override fun setJob(job: Job) {
                addJob("attemptSearch", job)
            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<List<RepositoryResponse>>) {
                val repositoryList: ArrayList<RepositoryEntity> = ArrayList()
                for (repositoryResponse in response.body) {
                    repositoryResponse.convert(checkIfRepoStarredBefore(repositoryResponse.repositoryId))
                        .let { repositoryList.add(it) }
                }
                updateLocalDb(repositoryList)
                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericApiResponse<List<RepositoryResponse>>> {
                return repoFetcherMainService.getUserRepos(ownerName, page)
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


    private fun noPreviousListFound(): LiveData<DataState<MainViewState>> {

        return object : LiveData<DataState<MainViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.data(
                    data = null,
                    response = Response(SUCCESS_GENERAL, ResponseType.None())
                )
            }
        }
    }

    fun checkIfRepoStarredBefore(repoId: Int): Boolean {
        starredRepoDao.searchById(repoId).let {
            it?.id?.let {
                return true
            }
        }
        return false
    }

    fun addToFavList(repositoryEntity: RepositoryEntity) {
        starredRepoDao.insertAndReplace(
            StarredRepoEntity(
                id = repositoryEntity.id,
                name = repositoryEntity.name,
                ownerName = repositoryEntity.ownerName
            )
        )
        repositoryDao.updateFavListStatus(true, repositoryEntity.id)
    }

    fun deleteFromFavList(repositoryEntity: RepositoryEntity) {
        starredRepoDao.delete(
            StarredRepoEntity(
                repositoryEntity.id,
                repositoryEntity.name,
                repositoryEntity.ownerName
            )
        )
        repositoryDao.updateFavListStatus(false, repositoryEntity.id)
    }

    companion object {
        private const val TAG = "MainRepository"
    }

}

private fun RepositoryResponse.convert(starSituation: Boolean): RepositoryEntity {
    return RepositoryEntity(
        this.repositoryId,
        this.openIssuesCount,
        this.starsCount,
        this.repositoryName!!,
        this.owner?.ownerName!!,
        this.owner?.avatarUrl!!,
        starSituation
    )
}
