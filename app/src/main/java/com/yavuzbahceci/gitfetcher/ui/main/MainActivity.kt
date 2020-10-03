package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.ui.BaseActivity
import com.yavuzbahceci.gitfetcher.view_models.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tool_bar.setOnClickListener {
            //
        }

        viewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.viewState.observe(this, Observer {
            //
        })
    }

}