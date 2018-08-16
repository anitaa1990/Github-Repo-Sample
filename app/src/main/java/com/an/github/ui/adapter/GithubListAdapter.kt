package com.an.github.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.an.github.BR
import com.an.github.databinding.ProgressItemBinding
import com.an.github.databinding.GithubItemBinding
import com.an.github.datasource.state.LoadingState
import com.an.github.model.Repository
import com.an.github.utils.RepoDiffCallback

class GithubListAdapter: PagedListAdapter<Repository, RecyclerView.ViewHolder>(RepoDiffCallback.diffCallback) {

    private val TYPE_PROGRESS = 0
    private val TYPE_ITEM = 1

    private var loadingState: LoadingState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == TYPE_PROGRESS) {
            val headerBinding = ProgressItemBinding.inflate(layoutInflater, parent, false)
            return NetworkStateItemViewHolder(headerBinding)

        } else {
            val itemBinding = GithubItemBinding.inflate(layoutInflater, parent, false)
            return GithubItemViewHolder(itemBinding)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GithubItemViewHolder) {
            holder.bindTo(getItem(position)!!)

        } else {
            (holder as NetworkStateItemViewHolder).bindTo(loadingState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return loadingState != null && loadingState !== LoadingState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }


    fun setLoadingState(newLoadingState: LoadingState) {
        val previousState = this.loadingState
        val previousExtraRow = hasExtraRow()
        this.loadingState = newLoadingState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newLoadingState) {
            notifyItemChanged(itemCount - 1)
        }
    }


    public override fun getItem(position: Int): Repository? {
        return super.getItem(position)
    }

    open class GithubItemViewHolder(private val binding: GithubItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(repository: Repository) {
            binding.setVariable(BR.repository, repository)
            binding.executePendingBindings()
        }
    }



    open class NetworkStateItemViewHolder(private val binding: ProgressItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bindTo(loadingState: LoadingState?) {
            if (loadingState != null && loadingState.status === LoadingState.Status.RUNNING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            if (loadingState != null && loadingState.status === LoadingState.Status.FAILED) {
                binding.errorMsg.visibility = View.VISIBLE
                binding.errorMsg.text = loadingState.msg

            } else {
                binding.errorMsg.visibility = View.GONE
            }
        }
    }
}