package com.example.movie_mvi_compose.data.network.errorHandling


import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

abstract class SafeApi {
    // private val mutex = Mutex()
    private var flag: Boolean = false

    suspend fun <T> safeApi(call: suspend () -> Response<T>): Resource<T> {

/*        return if (!mutex.isLocked) mutex.withLock { apiTry { call.invoke() } }
        else   null*/
        return apiTry { call.invoke() }

    }


    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(
                    data = it,
                    headers = response.headers(),
                    code = response.code()
                )
            }
        }
        return Resource.ApiError(
            message = response.message(),
            error = response.errorBody()?.string()!!,
            code = response.code(),
            totalError = "${response.message()} // ${
                response.errorBody()?.string()
            } // ${response.code()}"
        )
    }

    private suspend fun <T> apiTry(call: suspend () -> Response<T>): Resource<T> {
        return try {
            handleResponse(call.invoke())
        } catch (e: NoInternetException) {
            Resource.NetworkError(message = "${e.message}")
        } catch (e: UnknownHostException){
            Resource.NetworkError(message = "${e.message}")
        } catch (e: SocketException){
            Resource.NetworkError(message = "${e.message}")
        } catch (t: Throwable) {
            if (!flag) {
                flag = true
                delay(3000)
                apiTry(call)
            } else {
                flag = false
                Resource.UnknownError(message = "${t.message}//${t.cause}")
            }
        }
    }
}
class NoInternetException(message: String): IOException(message)