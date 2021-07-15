package com.example.movie_mvi_compose.data.network.errorHandling



sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Empty<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

