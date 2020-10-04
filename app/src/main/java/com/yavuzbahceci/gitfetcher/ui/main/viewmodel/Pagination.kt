package com.yavuzbahceci.gitfetcher.ui.main.viewmodel

import android.util.Log
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent.SearchAttemptEvent
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState

private const val TAG = "Pagination"

fun MainViewModel.resetPage() {
    val update = getCurrentViewStateOrNew()
    update.listRepoFields.page = 1
    setViewState(update)
}

fun MainViewModel.loadFirstPage() {
    setQueryInProgress(true)
    setQueryExhausted(false)
    resetPage()
    setStateEvent(SearchAttemptEvent())
}

fun MainViewModel.incrementPageNumber() {
    val update = getCurrentViewStateOrNew()
    val page = update.copy().listRepoFields.page
    update.listRepoFields.page = page + 1
    setViewState(update)
}

fun MainViewModel.nextPage() {
    if (!getIsQueryExhausted() && !getIsQueryInProgress()){
        Log.d(TAG, "nextPage: Attempting to load next page")
        incrementPageNumber()
        setQueryInProgress(true)
        setStateEvent(SearchAttemptEvent())
    }
}

fun MainViewModel.handleIncomingListData(viewState: MainViewState) {
    setQueryExhausted(viewState.listRepoFields.isQueryExhausted)
    setQueryInProgress(viewState.listRepoFields.isQueryInProgress)
    setRepoListData(viewState.listRepoFields.repoList)
}

