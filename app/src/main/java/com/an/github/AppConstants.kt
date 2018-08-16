package com.an.github

interface AppConstants {

    val INTENT_DETAIL: String
        get() = "intent_repository"

    val QUERY_DATE: String
        get() = "created:>%s"

    val SORT_BY_STARS: String
        get() = "stars"

    val ORDER_DESC: String
        get() = "desc"


    val DEFAULT_PAGE_SIZE: Int
        get() = 20


    val DEFAULT_PAGE_NUM: Long
        get() = 1
}