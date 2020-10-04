package com.yavuzbahceci.gitfetcher.di.main

import com.yavuzbahceci.gitfetcher.ui.main.RepoDetailFragment
import com.yavuzbahceci.gitfetcher.ui.main.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeRepoDetailFragment(): RepoDetailFragment

    @ContributesAndroidInjector()
    abstract fun contributeRepoListFragment(): RepoListFragment
}