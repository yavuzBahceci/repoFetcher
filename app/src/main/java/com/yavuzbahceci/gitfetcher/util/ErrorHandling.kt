package com.yavuzbahceci.gitfetcher.util

class ErrorHandling {

    companion object{

        const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host"
        const val UNABLE_TO_DO_OPERATION_WO_INTERNET = "Can't do that operation without an internet connection"
        const val ERROR_CHECK_NETWORK_CONNECTION = "Check network connection."
        const val EMPTY_RESULT = "This username does not exist, or user has no public repo"

        const val ERROR_UNKNOWN = "Unknown error"

        fun isNetworkError(msg: String): Boolean = when{
            msg.contains(UNABLE_TO_RESOLVE_HOST) -> true
            else-> false
        }
    }
}