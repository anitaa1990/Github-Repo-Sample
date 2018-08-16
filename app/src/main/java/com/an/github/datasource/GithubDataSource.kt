package com.an.github.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.an.github.AppConstants
import com.an.github.datasource.state.LoadingState
import com.an.github.model.Repository
import com.an.github.rest.RestApi
import com.an.github.rest.RestApiFactory
import com.an.github.utils.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GithubDataSource : PageKeyedDataSource<Long, Repository>(), AppConstants {

    private var restApi: RestApi = RestApiFactory.create()
    var loadingState : MutableLiveData<LoadingState> = MutableLiveData()
    var initialLoading: MutableLiveData<LoadingState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Repository>) {
        initialLoading.postValue(LoadingState.LOADING)
        loadingState.postValue(LoadingState.LOADING)

        val queryDate: String = String.format(QUERY_DATE, AppUtils.getLastWeekDate())
        restApi.fetchRepos(queryDate, SORT_BY_STARS, ORDER_DESC, DEFAULT_PAGE_NUM, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { apiResponse ->
                            if(apiResponse.isSuccessful && !AppUtils.isEmpty(apiResponse.body()?.items)) {
                                callback.onResult(apiResponse.body()!!.items, null, 2L)
                                initialLoading.postValue(LoadingState.LOADED)
                                loadingState.postValue(LoadingState.LOADED)

                            } else {
                                initialLoading.postValue(LoadingState(LoadingState.Status.FAILED, apiResponse.message()))
                                loadingState.postValue(LoadingState(LoadingState.Status.FAILED, apiResponse.message()))
                            }

                        },
                        { error ->
                            val errorMessage: String = if (error == null) "unknown error" else error.message!!
                            loadingState.postValue(LoadingState(LoadingState.Status.FAILED, errorMessage))
                            initialLoading.postValue(LoadingState(LoadingState.Status.FAILED, errorMessage))
                        }
                )
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {
        loadingState.postValue(LoadingState.LOADING)

        val queryDate: String = String.format(QUERY_DATE, AppUtils.getLastWeekDate())
        restApi.fetchRepos(queryDate, SORT_BY_STARS, ORDER_DESC, params.key, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { apiResponse ->
                            if(apiResponse.isSuccessful && apiResponse.body()?.items != null) {

                                val totalCount: Long = (apiResponse.body()!!.totalCount) / DEFAULT_PAGE_SIZE
                                val nextKey = (if (params.key === totalCount) null else params.key + 1)

                                callback.onResult(apiResponse.body()!!.items, nextKey)
                                loadingState.postValue(LoadingState.LOADED)


                            } else {
                                loadingState.postValue(LoadingState(LoadingState.Status.FAILED, apiResponse.message()))
                            }

                        },
                        { error ->
                            val errorMessage: String = if (error == null) "unknown error" else error.message!!
                            loadingState.postValue(LoadingState(LoadingState.Status.FAILED, errorMessage))
                        }
                )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {

    }
}