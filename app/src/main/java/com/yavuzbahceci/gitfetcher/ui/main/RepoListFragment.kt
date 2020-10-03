package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent
import com.yavuzbahceci.gitfetcher.ui.main.state.SearchField
import com.yavuzbahceci.gitfetcher.util.ApiEmptyResponse
import com.yavuzbahceci.gitfetcher.util.ApiErrorResponse
import com.yavuzbahceci.gitfetcher.util.ApiSuccessResponse
import kotlinx.android.synthetic.main.fragment_repo_list.*


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

        submit_button.setOnClickListener {
            searchRepos()
        }
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.searchField?.search_text?.let { text ->
                // set search edit text
                user_name_edit_text.setText(text)
            }
        })
    }

    private fun navDetailFragment() {
        findNavController().navigate(R.id.action_repoListFragment_to_repoDetailFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setSearchField(
            // Set the edit_text TODO()
            SearchField("")
        )
    }

    fun searchRepos() {
        viewModel.setStateEvent(
            MainStateEvent.SearchAttemptEvent(
                user_name_edit_text.text.toString()
            )
        )
    }

    companion object {
        private const val TAG = "RepoListFragment"
    }
}