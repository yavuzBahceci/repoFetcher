package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.util.ApiEmptyResponse
import com.yavuzbahceci.gitfetcher.util.ApiErrorResponse
import com.yavuzbahceci.gitfetcher.util.ApiSuccessResponse


/**
 * A simple [Fragment] subclass.
 * Use the [RepoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoListFragment : BaseMainFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.testRetrofit().observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiSuccessResponse -> {
                    Log.d(TAG, "onViewCreated: LOGIN RESPONSE: ${response.body}")
                }

                is ApiErrorResponse -> {
                    Log.d(TAG, "onViewCreated: LOGIN RESPONSE: ${response.errorMessage}")

                }

                is ApiEmptyResponse -> {
                    Log.d(TAG, "onViewCreated: LOGIN RESPONSE: Empty Response")

                }
            }
        })
    }

    private fun navDetailFragment() {
        findNavController().navigate(R.id.action_repoListFragment_to_repoDetailFragment)
    }

    companion object {
        private const val TAG = "RepoListFragment"
    }
}