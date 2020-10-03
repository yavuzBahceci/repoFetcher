package com.yavuzbahceci.gitfetcher.ui

import android.util.Log
import com.yavuzbahceci.gitfetcher.ui.ResponseType.*
import com.yavuzbahceci.gitfetcher.util.InternetChecker
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity(), DataStateChangeListener{

    private val TAG: String = "AppDebug"

    @Inject
    lateinit var internetChecker: InternetChecker

    override fun onDataStateChange(dataState: DataState<*>?) {

        dataState?.let {
            GlobalScope.launch(Main) {
                displayProgressBar(it.loading.isLoading)
                it.error?.let { errorEvent ->
                    handleStateError(errorEvent)
                }
                it.data?.let {
                    it.response?.let { responseEvent ->
                        handleStateResponse(responseEvent)
                    }
                }
            }
        }
    }

    private fun handleStateResponse(responseEvent: Event<Response>) {
        responseEvent.getContentIfNotHandled()?.let {
            when(it.responseType){

                is Dialog -> {
                    it.message?.let {  message ->
                        displaySuccessDialog(message)
                    }
                }

                is Toast -> {
                    it.message?.let {  message ->
                        displayToast(message)
                    }
                }

                is None -> {
                    Log.d(TAG, "handleStateError: ${it.message}", )
                }

            }
        }
    }

    private fun handleStateError(errorEvent: Event<StateError>) {

        errorEvent.getContentIfNotHandled()?.let {
            when(it.response.responseType){

                is Dialog -> {
                    it.response.message?.let {  message ->
                        displayErrorDialog(message)
                    }
                }

                is Toast -> {
                    it.response.message?.let {  message ->
                        displayToast(message)
                    }
                }

                is None -> {
                    Log.e(TAG, "handleStateError: ${it.response.message}", )
                }

            }
        }
    }

    abstract fun displayProgressBar(boolean: Boolean)

}