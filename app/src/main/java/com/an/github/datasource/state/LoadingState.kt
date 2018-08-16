package com.an.github.datasource.state

class LoadingState(val status: Status, val msg: String) {

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    companion object {
        val LOADED: LoadingState = LoadingState(Status.SUCCESS, "Success")
        val LOADING: LoadingState = LoadingState(Status.RUNNING, "Running")
    }
}