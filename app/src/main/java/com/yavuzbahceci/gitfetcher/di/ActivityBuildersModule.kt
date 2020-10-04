package com.yavuzbahceci.gitfetcher.di

import com.yavuzbahceci.gitfetcher.di.main.MainFragmentBuildersModule
import com.yavuzbahceci.gitfetcher.di.main.MainModule
import com.yavuzbahceci.gitfetcher.di.main.MainScope
import com.yavuzbahceci.gitfetcher.di.main.MainViewModelModule
import com.yavuzbahceci.gitfetcher.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}