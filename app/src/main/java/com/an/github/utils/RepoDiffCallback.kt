package com.an.github.utils

import androidx.recyclerview.widget.DiffUtil
import com.an.github.model.Repository

class RepoDiffCallback {

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem == newItem
        }
    }
}