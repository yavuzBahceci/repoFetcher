package com.yavuzbahceci.gitfetcher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yavuzbahceci.gitfetcher.R


/**
 * A simple [Fragment] subclass.
 * Use the [RepoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoDetailFragment : BaseMainFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    companion object {

    }
}