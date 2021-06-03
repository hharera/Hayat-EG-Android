package com.mindorks.example.coroutines.utils

import java.lang.Exception

data class Response<out T> private constructor(val status: Status, val data: T?, val error: Exception?) {

    companion object {

        fun <T> success(data: T?): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Exception?, data: T?): Response<T> {
            return Response(Status.ERROR, data, error)
        }

        fun <T> loading(data: T?): Response<T> {
            return Response(Status.LOADING, data, null)
        }

    }

}