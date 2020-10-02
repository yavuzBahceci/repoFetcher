package com.yavuzbahceci.gitfetcher.ui.main

import androidx.lifecycle.ViewModel
import com.yavuzbahceci.gitfetcher.repository.main.MainRepository
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
        val mainRepository: MainRepository
    ): ViewModel() {

}