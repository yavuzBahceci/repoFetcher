package com.yavuzbahceci.gitfetcher.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yavuzbahceci.gitfetcher.api.main.data.RepositoryResponse
import com.yavuzbahceci.gitfetcher.repository.main.MainRepository
import com.yavuzbahceci.gitfetcher.ui.BaseViewModel
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent.*
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import com.yavuzbahceci.gitfetcher.ui.main.state.SearchField
import com.yavuzbahceci.gitfetcher.util.AbsentLiveData
import com.yavuzbahceci.gitfetcher.util.GenericApiResponse
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
        val mainRepository: MainRepository
    ): BaseViewModel<MainStateEvent, MainViewState>() {


    override fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when(stateEvent) {
            is SearchAttemptEvent -> {
                AbsentLiveData.create()
            }

            is checkPreviousSearchEvent -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setSearchField(searchField: SearchField) {
        val update = getCurrentViewStateOrNew()
        if (update.searchField == searchField){
            return
        }
        update.searchField = searchField
        _viewState.value = update
    }

    override fun initNewViewState(): MainViewState {
        return MainViewState()
    }
}