package com.example.movie_mvi_compose.data.network.util

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.*
import java.lang.reflect.Type

/**
 * A Retrofit converter to return objects wrapped in [NetworkResponse] class
 *
 * @param T The type of the successful response model
 * @param U The type of the error response model
 * @param successBodyType The type of the successful response model in [NetworkResponse]
 * @param errorConverter The converter to extract error information from [ResponseBody]
 * @constructor Creates a DeferredNetworkResponseAdapter
 */

internal class DeferredNetworkResponseAdapter<T : Any, U : Any>(
    private val successBodyType: Type,
    private val errorConverter: Converter<ResponseBody, U>
) : CallAdapter<T, Deferred<NetworkResponse<T, U>>> {

    /**
     * This is used to determine the parameterize type of the object
     * being handled by this adapter. For example, the response type
     * in Call<Repo> is Repo.
     */
    override fun responseType(): Type = successBodyType

    /**
     * Returns an instance of [T] by modifying a [Call] object
     *
     * @param call The call object to be converted
     * @return The T instance wrapped in a [NetworkResponse] class wrapped in [Deferred]
     */
    override fun adapt(call: Call<T>): Deferred<NetworkResponse<T, U>> {
        val deferred = CompletableDeferred<NetworkResponse<T, U>>()

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, throwable: Throwable) {
                try {
                    val networkResponse = throwable.extractNetworkResponse<T, U>(errorConverter)
                    deferred.complete(networkResponse)
                } catch (t: Throwable) {
                    deferred.completeExceptionally(t)
                }
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResponse = ResponseHandler.handle(response, successBodyType, errorConverter)
                deferred.complete(networkResponse)
            }
        })

        return deferred
    }
}