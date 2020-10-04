package com.yavuzbahceci.gitfetcher.ui.main.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.bumptech.glide.RequestManager
import com.yavuzbahceci.gitfetcher.repository.main.MainRepository
import com.yavuzbahceci.gitfetcher.ui.BaseViewModel
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.Loading
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent.*
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    val mainRepository: MainRepository,
    sharedPreferences: SharedPreferences,
    private val requestManager: RequestManager
) : BaseViewModel<MainStateEvent, MainViewState>() {


    override fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is SearchAttemptEvent -> {
                mainRepository.attemptSearch(
                    ownerName = getSearchQuery(),
                    page = getPage()
                )
            }

            is ChangeStarOptionEvent -> {
                object : LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        value = DataState(
                            null,
                            Loading(false),
                            null
                        )
                    }
                }
            }

            is checkPreviousSearchEvent -> {
                object : LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        value = DataState(
                            null,
                            Loading(false),
                            null
                        )
                    }
                }
            }
            is None -> {

                object : LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        value = DataState(
                            null,
                            Loading(false),
                            null
                        )
                    }
                }
            }
            is CheckIsRepoStarred -> {
                object : LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        value = DataState(
                            null,
                            Loading(false),
                            null
                        )
                    }
                }
            }
        }
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