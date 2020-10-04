package com.yavuzbahceci.gitfetcher.ui.main.state

sealed class MainStateEvent {

    class SearchAttemptEvent(): MainStateEvent()

    class ChangeStarOptionEvent: MainStateEvent()

    class None: MainStateEvent()

    class CheckIsRepoStarred: MainStateEvent()

    class checkPreviousSearchEvent: MainStateEvent()
}