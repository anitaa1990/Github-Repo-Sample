package com.an.github

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.an.github.model.Repository
import io.reactivex.android.schedulers.AndroidSchedulers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.an.github", appContext.packageName)
    }


    @Test
    fun testFetchTendingRepos() {

        val appContext = InstrumentationRegistry.getTargetContext()
        val appController = AppController.create(appContext)

        appController.getRestApi()
                .fetchRepos("created:>2018-08-14", "stars", "desc", 1, 10)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { apiResponse ->

                            if(apiResponse.isSuccessful) {
                                for (repository in apiResponse.body()!!.items) {
                                    System.out.println("---------------")
                                    System.out.println(repository.name)
                                    System.out.println(repository.owner.login)
                                    System.out.println(repository.url)
                                    System.out.println(repository.createdAt)
                                }
                            }

                        },
                        { error ->
                            System.out.println(error)
                        }
                )

        Thread.sleep(5000)
    }
}
