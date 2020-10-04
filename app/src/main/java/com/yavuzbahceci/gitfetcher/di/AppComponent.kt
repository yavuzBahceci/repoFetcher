package com.yavuzbahceci.gitfetcher.di

import android.app.Application
import com.yavuzbahceci.gitfetcher.base.BaseApp
import com.yavuzbahceci.gitfetcher.util.InternetChecker
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApp> {

    val internetChecker: InternetChecker

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
