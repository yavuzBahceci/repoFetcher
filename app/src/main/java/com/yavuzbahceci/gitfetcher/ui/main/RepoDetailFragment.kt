package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity
import com.yavuzbahceci.gitfetcher.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import kotlinx.android.synthetic.main.fragment_repo_detail.repo_owner
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.layout_repo_list_item.*


/**
 * A simple [Fragment] subclass.
 * Use the [RepoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoDetailFragment : BaseMainFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            stateChangeListener.onDataStateChange(dataState)
        })

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState.detailRepoFields.repositoryEntity?.let {
                setRepoDetailProperties(it)
            }
        })
    }

    fun setRepoDetailProperties(repositoryEntity: RepositoryEntity){
        requestManager
            .load(repositoryEntity.avatarUrl)
            .into(owner_avatar)

        repo_owner.setText(repositoryEntity.ownerName)
        repo_name_detail.setText(repositoryEntity.name)
        star_count.setText("Stars: ${repositoryEntity.stargazerCount}")
        open_issues_count.setText("Open Issues: ${repositoryEntity.openIssuesCount}")
    }

    fun changeStarOption() {
        val id: Int = 0
        viewModel.setStateEvent(
            MainStateEvent.ChangeStarOptionEvent()
        )
    }
    companion object {

    }
}