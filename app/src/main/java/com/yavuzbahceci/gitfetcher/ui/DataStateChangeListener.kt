package com.yavuzbahceci.gitfetcher.ui

interface DataStateChangeListener {

    fun onDataStateChange(dataState: DataState<*>?)
}