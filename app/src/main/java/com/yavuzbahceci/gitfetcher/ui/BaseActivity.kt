package com.yavuzbahceci.gitfetcher.ui

import com.yavuzbahceci.gitfetcher.util.InternetChecker
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity(){

    private val TAG: String = "AppDebug"

    @Inject
    lateinit var internetChecker: InternetChecker


}