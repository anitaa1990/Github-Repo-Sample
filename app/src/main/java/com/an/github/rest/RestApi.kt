package com.an.github.rest


import com.an.github.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.an.github.model.Repository
import io.reactivex.Single
import retrofit2.Response


interface RestApi {

    //https://api.github.com/search/repositories?q=created:%3E2018-08-14&sort=stars&order=desc&per_page=25&page=1

    @GET("/search/repositories")
    fun fetchRepos(@Query("q") q: String,
                   @Query("sort") sort: String,
                   @Query("order") order: String,
                   @Query("page") page: Long,
                   @Query("per_page") perPage: Int): Single<Response<ApiResponse>>

}