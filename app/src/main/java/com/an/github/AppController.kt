package com.an.github

import io.reactivex.Scheduler
import android.app.Application
import android.content.Context
import com.an.github.rest.RestApi
import com.an.github.rest.RestApiFactory
import io.reactivex.schedulers.Schedulers


class AppController : Application() {

    private var restApi: RestApi? = null
    private var scheduler: Scheduler? = null

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private operator fun get(context: Context): AppController {
            return context.applicationContext as AppController
        }

        fun create(context: Context): AppController {
            return AppController.get(context)
        }
    }

    fun getRestApi(): RestApi {
        if (restApi == null) {
            restApi = RestApiFactory.create()
        }
        return restApi!!
    }

    fun subscribeScheduler(): Scheduler {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }

        return scheduler!!
    }
}