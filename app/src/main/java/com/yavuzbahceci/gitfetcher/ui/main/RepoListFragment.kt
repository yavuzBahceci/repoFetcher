package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent
import com.yavuzbahceci.gitfetcher.ui.main.state.MainViewState
import com.yavuzbahceci.gitfetcher.ui.main.viewmodel.*
import com.yavuzbahceci.gitfetcher.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_repo_list.*


/**
 * A simple [Fragment] subclass.
 * Use the [RepoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoListFragment : BaseMainFragment(), RepositoryListAdapter.Interaction {


    private lateinit var recyclerAdapter: RepositoryListAdapter

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
            viewModel.loadFirstPage()
        }
        initRecyclerview()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState.searchField?.search_text?.let { text ->
                user_name_edit_text.setText(text)
            }

            if (viewState != null) {
                recyclerAdapter.submitList(
                    repoList = viewState.listRepoFields.repoList,
                    isQueryExhausted = viewState.listRepoFields.isQueryExhausted
                )
            }

        })

        viewModel.dataState.observe(this, { dataState ->
            handlePagination(dataState)
            stateChangeListener.onDataStateChange(dataState)


        })

        viewModel.viewState.observe(this, {
            Log.d(TAG, "subscribeObservers:  ViewState $it")
        })
    }

    private fun handlePagination(dataState: DataState<MainViewState>?) {

        // handle incoming data from DataState

        dataState?.data?.let {
            it.data?.let {
                it.getContentIfNotHandled()?.let {
                    viewModel.handleIncomingListData(it)
                }
            }
        }
        // check for pagination end aka nomoreresult
        // must to dis b/c server will return ApiErrorResponse if page is not valid -> No more data

        dataState?.error?.let { event ->
            event.getContentIfNotHandled()
            viewModel.setQueryExhausted(true)
        }
    }

    private fun navDetailFragment() {
        findNavController().navigate(R.id.action_repoListFragment_to_repoDetailFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun searchRepos() {
        viewModel.setStateEvent(
            MainStateEvent.SearchAttemptEvent()
        )
    }

    private fun initRecyclerview() {
        repo_list_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@RepoListFragment.context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(20)
            removeItemDecoration(topSpacingItemDecoration)
            addItemDecoration(topSpacingItemDecoration)
            recyclerAdapter = RepositoryListAdapter(
                this@RepoListFragment
            )

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == recyclerAdapter.itemCount.minus(1)) {
                        Log.d(TAG, "onScrollStateChanged: ")
                        viewModel.nextPage()
                    }
                }
            })

            adapter = recyclerAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        repo_list_recyclerview.adapter = null
    }

    override fun onItemSelected(position: Int, item: RepositoryEntity) {
        viewModel.setRepoDetail(item)
        findNavController().navigate(R.id.action_repoListFragment_to_repoDetailFragment)
    }

    companion object {
        private const val TAG = "RepoListFragment"
    }


}