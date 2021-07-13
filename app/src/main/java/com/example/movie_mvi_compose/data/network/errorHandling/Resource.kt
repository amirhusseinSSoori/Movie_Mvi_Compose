package com.example.movie_mvi_compose.data.network.errorHandling

import okhttp3.Headers
import okhttp3.ResponseBody
//
//sealed class Resource<T>(
//val data: T? = null,
//val headers: Headers? = null,
//val message: String? = null,
//val error: String? = null,
//val code: Int? = null,
//val totalError: String? = null
//) {
//    class Empty<T>:Resource<T>()
//    class Success <T> (data: T ,headers: Headers ,code: Int): Resource<T>(data ,headers ,null ,null ,code)
//    class ApiError<T> (message: String,error: String,code: Int ,totalError: String): Resource<T>(null,null,message,error,code,totalError)
//    class NetworkError<T>(message: String): Resource<T>(null,null,message)
//    class UnknownError<T>(message: String): Resource<T>(null,null,message)
//}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Empty<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

