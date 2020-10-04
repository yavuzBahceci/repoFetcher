package com.yavuzbahceci.gitfetcher.ui.main.state

sealed class MainStateEvent {

    data class SearchAttemptEvent(
        val ownerName: String,
        val page: Int
    ): MainStateEvent()

    data class ChangeStarOptionEvent(
        val repoId: Int
    ): MainStateEvent()

    class None: MainStateEvent()

    class CheckIsRepoStarred: MainStateEvent()

    class checkPreviousSearchEvent: MainStateEvent()
}