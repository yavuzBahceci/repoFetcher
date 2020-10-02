package com.yavuzbahceci.gitfetcher.ui.main.state

sealed class MainStateEvent {

    data class SearchAttemptEvent(
        val ownerName: String
    ): MainStateEvent()

    class checkPreviousSearchEvent: MainStateEvent()
}