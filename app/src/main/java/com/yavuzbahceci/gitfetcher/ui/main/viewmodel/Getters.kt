package com.yavuzbahceci.gitfetcher.ui.main.viewmodel

fun MainViewModel.getPage(): Int {
    getCurrentViewStateOrNew().let {
        return it.listRepoFields.page
    }
}

fun MainViewModel.getSearchQuery(): String{
    getCurrentViewStateOrNew().let {
        return it.listRepoFields.searchQuery
    }
}

fun MainViewModel.getIsQueryExhausted(): Boolean {
    getCurrentViewStateOrNew().let {
        return it.listRepoFields.isQueryExhausted
    }
}

fun MainViewModel.getIsQueryInProgress(): Boolean {
    getCurrentViewStateOrNew().let {
        return it.listRepoFields.isQueryInProgress
    }
}

fun MainViewModel.getRepoId(): Int {
    getCurrentViewStateOrNew().let {
        return it.detailRepoFields.repositoryEntity?.id!!
    }
}