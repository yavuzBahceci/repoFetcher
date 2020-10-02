package com.yavuzbahceci.gitfetcher.di.main

import androidx.lifecycle.ViewModel
import com.yavuzbahceci.gitfetcher.di.ViewModelKey
import com.yavuzbahceci.gitfetcher.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindAuthViewModel(mainViewModel: MainViewModel): ViewModel

}