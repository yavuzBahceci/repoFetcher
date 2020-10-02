package com.yavuzbahceci.gitfetcher.ui.main.state


data class MainViewState(
    var searchField: SearchField? = SearchField()
)

data class SearchField(
    var search_text: String? = null
) {
    class SearchError{
        companion object {
            fun mustHasLetter(): String {
                return "You cannot search with empty string."
            }

            fun none(): String {
                return "None"
            }
        }
    }

    fun isValidForSearch(): String = if (search_text.isNullOrEmpty()){
        SearchError.mustHasLetter()
    } else {
        SearchError.none()
    }

}