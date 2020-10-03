package com.yavuzbahceci.gitfetcher.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetChecker
@Inject
constructor(
    val application: Application
) {

    private val TAG: String = "AppDebug"


    fun isConnectedToTheInternet(): Boolean{
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try{
            return cm.activeNetworkInfo.isConnected
        }catch (e: Exception){
            Log.e(TAG, "isConnectedToTheInternet: ${e.message}")
        }
        return false
    }


}