package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.ui.BaseActivity
import com.yavuzbahceci.gitfetcher.ui.ResponseType
import com.yavuzbahceci.gitfetcher.ui.ResponseType.*
import com.yavuzbahceci.gitfetcher.view_models.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {

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
        findNavController(R.id.main_nav_host_fragment).addOnDestinationChangedListener(this)
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(this, Observer { dataState ->
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled().let {
                        // getList
                    }
                }
                data.response.let { event ->
                    event?.getContentIfNotHandled().let {
                        when (it?.responseType) {
                            is Dialog -> {
                                // Show Dialog
                            }

                            is Toast -> {
                                // Show Toast
                            }

                            is None -> {
                                Log.e(Companion.TAG, "subscribeObservers: Response ${it.message}")
                            }
                        }
                    }

                }
            }

        })

        viewModel.viewState.observe(this, Observer {
            //
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.cancelActiveJobs()
    }

}