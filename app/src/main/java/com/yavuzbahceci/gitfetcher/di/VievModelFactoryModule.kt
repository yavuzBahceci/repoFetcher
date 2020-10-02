package com.yavuzbahceci.gitfetcher.di

import androidx.lifecycle.ViewModelProvider
import com.yavuzbahceci.gitfetcher.view_models.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}