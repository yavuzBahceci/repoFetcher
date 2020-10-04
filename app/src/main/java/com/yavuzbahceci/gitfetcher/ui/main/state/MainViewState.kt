package com.yavuzbahceci.gitfetcher.ui.main.state

import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity


data class MainViewState(
    var listRepoFields: ListRepoFields = ListRepoFields(),
    var detailRepoFields: DetailRepoFields = DetailRepoFields(),
    var searchField: SearchField? = SearchField()
) {
    data class ListRepoFields(
        var repoList: List<RepositoryEntity> = ArrayList<RepositoryEntity>(),
        var searchQuery: String = "",
        var isQueryInProgress: Boolean = false
    )

    data class DetailRepoFields(
        var repositoryEntity: RepositoryEntity? = null,
        var hasRepoStarred: Boolean = false
    )

}

data class SearchField(
    var search_text: String? = null
) {
    class SearchError {
        companion object {
            fun mustHasLetter(): String {
                return "You cannot search with empty string."
            }

            fun none(): String {
                return "None"
            }
        }
    }

    fun isValidForSearch(): String = if (search_text.isNullOrEmpty()) {
        SearchError.mustHasLetter()
    } else {
        SearchError.none()
    }

}

