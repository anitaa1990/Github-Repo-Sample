package com.an.github.datasource.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.an.github.datasource.GithubDataSource
import com.an.github.model.Repository


class GithubDataFactory: DataSource.Factory<Long, Repository>() {

    val mutableLiveData: MutableLiveData<GithubDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Repository> {
        var githubDataSource = GithubDataSource()
        mutableLiveData.postValue(githubDataSource)
        return githubDataSource
    }

}

