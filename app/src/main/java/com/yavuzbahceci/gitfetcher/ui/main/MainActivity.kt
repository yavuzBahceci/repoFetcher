package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.ui.BaseActivity
import com.yavuzbahceci.gitfetcher.view_models.ViewModelProviderFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
    }

}