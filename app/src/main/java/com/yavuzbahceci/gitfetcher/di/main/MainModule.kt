package com.yavuzbahceci.gitfetcher.di.main

import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import com.yavuzbahceci.gitfetcher.repository.main.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule{

    @MainScope
    @Provides
    fun provideMainApiService(retrofitBuilder: Retrofit.Builder): RepoFetcherMainService{
        return retrofitBuilder
            .build()
            .create(RepoFetcherMainService::class.java)
    }

    /*
    // Main Repo
    fun provideMainRepository(): MainRepository{
        return MainRepository()
    }*/

}