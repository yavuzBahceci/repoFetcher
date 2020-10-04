package com.yavuzbahceci.gitfetcher.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.bumptech.glide.RequestManager
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity
import com.yavuzbahceci.gitfetcher.repository.main.MainRepository
import com.yavuzbahceci.gitfetcher.ui.BaseViewModel
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent.*
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import com.yavuzbahceci.gitfetcher.ui.main.state.SearchField
import com.yavuzbahceci.gitfetcher.util.AbsentLiveData
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
        val mainRepository: MainRepository,
        sharedPreferences: SharedPreferences,
        private val requestManager: RequestManager
    ): BaseViewModel<MainStateEvent, MainViewState>() {


    override fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when(stateEvent) {
            is SearchAttemptEvent -> {
                mainRepository.attemptSearch(stateEvent.ownerName, stateEvent.page)
            }

            is ChangeStarOptionEvent -> {
                // add or delete fav list
                // main Repo
                AbsentLiveData.create()
            }

            is checkPreviousSearchEvent -> {
                AbsentLiveData.create()
            }
            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setQuery(query: String) {
        val update = getCurrentViewStateOrNew()
        if (query.equals(update.listRepoFields.searchQuery)){
            return
        }
        update.listRepoFields.searchQuery = query
        _viewState.value = update
    }

    fun setRepoListData(repoList: List<RepositoryEntity>) {
        val update = getCurrentViewStateOrNew()
        update.listRepoFields.repoList = repoList
        _viewState.value = update
    }

    override fun initNewViewState(): MainViewState {
        return MainViewState()
    }

    fun cancelActiveJobs() {
        mainRepository.cancelActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}