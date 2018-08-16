package com.an.github.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.an.github.AppConstants
import com.an.github.R
import com.an.github.databinding.GithubDetailActivityBinding
import com.an.github.model.Repository
import com.squareup.picasso.Picasso

class GithubDetailActivity: AppCompatActivity(), AppConstants {


    private lateinit var binding: GithubDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail)

        val repository = intent.getParcelableExtra<Repository>(INTENT_DETAIL)
        binding.repository = repository

        Picasso.get().load(repository.owner.avatarUrl).placeholder(R.mipmap.ic_launcher).into(binding.repoImage)
    }
}