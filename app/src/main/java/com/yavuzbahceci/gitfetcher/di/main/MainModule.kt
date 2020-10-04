package com.yavuzbahceci.gitfetcher.di.main

import android.content.SharedPreferences
import com.yavuzbahceci.gitfetcher.api.main.RepoFetcherMainService
import com.yavuzbahceci.gitfetcher.persistence.AppDatabase
import com.yavuzbahceci.gitfetcher.persistence.daos.RepositoryDao
import com.yavuzbahceci.gitfetcher.persistence.daos.StarredRepoDao
import com.yavuzbahceci.gitfetcher.repository.main.MainRepository
import com.yavuzbahceci.gitfetcher.util.InternetChecker
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideMainApiService(retrofitBuilder: Retrofit.Builder): RepoFetcherMainService {
        return retrofitBuilder
            .build()
            .create(RepoFetcherMainService::class.java)
    }


    @MainScope
    @Provides
    fun provideMainRepository(
        starredRepoDao: StarredRepoDao,
        repositoryDao: RepositoryDao,
        repoFetcherMainService: RepoFetcherMainService,
        internetChecker: InternetChecker,
        preferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): MainRepository {
        return MainRepository(
            starredRepoDao,
            repositoryDao,
            repoFetcherMainService,
            internetChecker,
            preferences,
            editor
        )
    }

    @MainScope
    @Provides
    fun provideRepositoryDao(db: AppDatabase): RepositoryDao {
        return db.getRepositoryDao()
    }

    @MainScope
    @Provides
    fun provideStarredRepoDao(db: AppDatabase): StarredRepoDao {
        return db.getStarredRepoDao()
    }

}