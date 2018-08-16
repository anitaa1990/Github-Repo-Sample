package com.an.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.an.github.AppConstants
import com.an.github.datasource.factory.GithubDataFactory
import com.an.github.datasource.state.LoadingState
import com.an.github.model.Repository
import java.util.concurrent.Executors

class GithubListViewModel : ViewModel(), AppConstants {

    lateinit var loadingState: LiveData<LoadingState>
    lateinit var refreshState: LiveData<LoadingState>
    lateinit var githubLiveData: LiveData<PagedList<Repository>>

    init {
        initNetworkRequest()
    }

    private fun initNetworkRequest() {
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE)
                .setPageSize(DEFAULT_PAGE_SIZE).build()

        val githubDataFactory = GithubDataFactory()
        loadingState = Transformations.switchMap(githubDataFactory.mutableLiveData) { dataSource ->
            dataSource.loadingState
        }

        refreshState = Transformations.switchMap(githubDataFactory.mutableLiveData) { dataSource ->
            dataSource.initialLoading
        }

        githubLiveData = LivePagedListBuilder(githubDataFactory, pagedListConfig)
                .setFetchExecutor(Executors.newFixedThreadPool(1))
                .build()
    }


    fun isRefreshing(loadingState: LoadingState): Boolean {
        return (githubLiveData.value == null || (githubLiveData.value!!.size == 0 && loadingState.status == LoadingState.Status.RUNNING))
    }

    fun hasFailed(loadingState: LoadingState): Boolean {
        return (githubLiveData.value == null || (githubLiveData.value!!.size == 0 && loadingState.status == LoadingState.Status.FAILED))
    }
}