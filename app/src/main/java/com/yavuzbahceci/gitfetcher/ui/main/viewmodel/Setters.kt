package com.yavuzbahceci.gitfetcher.ui.main.viewmodel

import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity

fun MainViewModel.setRepoDetail(repositoryEntity: RepositoryEntity){
    val update = getCurrentViewStateOrNew()
    update.detailRepoFields.repositoryEntity = repositoryEntity
    setViewState(update)
}

fun MainViewModel.setQueryExhausted(isExhausted: Boolean){
    val update = getCurrentViewStateOrNew()
    update.listRepoFields.isQueryExhausted = isExhausted
    setViewState(update)
}

fun MainViewModel.setQueryInProgress(isInProgress: Boolean){
    val update = getCurrentViewStateOrNew()
    update.listRepoFields.isQueryExhausted = isInProgress
    setViewState(update)
}

fun MainViewModel.setHasRepoStarred(hasRepoStarred: Boolean){
    val update = getCurrentViewStateOrNew()
    update.detailRepoFields.hasRepoStarred = hasRepoStarred
    setViewState(update)
}

fun MainViewModel.setQuery(query: String) {
    val update = getCurrentViewStateOrNew()
    if (query.equals(update.listRepoFields.searchQuery)) {
        return
    }
    update.listRepoFields.searchQuery = query
    setViewState(update)
}

fun MainViewModel.setRepoListData(repoList: List<RepositoryEntity>) {
    val update = getCurrentViewStateOrNew()
    update.listRepoFields.repoList = repoList
    setViewState(update)
}