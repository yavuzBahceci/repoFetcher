package com.yavuzbahceci.gitfetcher.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.yavuzbahceci.gitfetcher.R
import com.yavuzbahceci.gitfetcher.persistence.entities.RepositoryEntity
import com.yavuzbahceci.gitfetcher.util.GenericViewHolder
import kotlinx.android.synthetic.main.layout_repo_list_item.view.*


class RepositoryListAdapter(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG: String = "AppDebug"
    private val NO_MORE_RESULTS = -1
    private val REPO_ITEM = 0
    private val NO_MORE_RESULTS_REPO_MARKER = RepositoryEntity(
        NO_MORE_RESULTS,
        0 ,
        0,
        "",
        "",
        ""
    )

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepositoryEntity>() {

        override fun areItemsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean {
            return oldItem == newItem
        }

    }
    private val differ =
        AsyncListDiffer(
            RepoRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){

            NO_MORE_RESULTS ->{
                Log.e(TAG, "onCreateViewHolder: No more results...")
                return GenericViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_no_results,
                        parent,
                        false
                    )
                )
            }

            REPO_ITEM -> {
                return RepoViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_repo_list_item,
                        parent,
                        false
                    ),
                    interaction = interaction
                )
            }
            else -> {
                return RepoViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_repo_list_item,
                        parent,
                        false
                    ),
                    interaction = interaction
                )
            }
        }
    }

    internal inner class RepoRecyclerChangeCallback(
        private val adapter: RepositoryListAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RepoViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(differ.currentList.get(position).id > -1){
            return REPO_ITEM
        }
        return differ.currentList.get(position).id
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun submitList(repoList: List<RepositoryEntity>?, isQueryExhausted: Boolean){
        val newList = repoList?.toMutableList()
        if (isQueryExhausted)
            newList?.add(NO_MORE_RESULTS_REPO_MARKER)
        differ.submitList(newList)
    }

    class RepoViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: RepositoryEntity) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.repo_name.text = item.name
            itemView.repo_owner.text = item.ownerName
            if (item.isFromFavList){
                itemView.list_star_logo.setImageResource(R.drawable.ic_star_fav_24dp)
            }else {
                itemView.list_star_logo.setImageResource(R.drawable.ic_star_checked_24dp)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: RepositoryEntity)
    }
}

