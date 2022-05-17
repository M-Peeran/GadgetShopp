package com.peeranm.gadgetshopp.core

class Resource<out T>(val status: Status, data: T?, message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(message: String): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}