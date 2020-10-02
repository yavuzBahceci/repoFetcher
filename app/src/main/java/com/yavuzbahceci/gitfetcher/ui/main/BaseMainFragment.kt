package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yavuzbahceci.gitfetcher.view_models.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseMainFragment: DaggerFragment(){

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    companion object {
        private const val TAG = "BaseMainFragment"
    }

}