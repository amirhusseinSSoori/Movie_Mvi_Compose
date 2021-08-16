package com.example.movie_mvi_compose.ui.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = channelFlow {
    val data = query().first()

    if (shouldFetch(data)) {
        val loading = launch {
            query().collect { send(DataState.Loading(it)) }
        }
        try {
            delay(2000)
            saveFetchResult(fetch())
            loading.cancel()
            query().collect { send(DataState.Data(it)) }
        } catch (t: Throwable) {
            loading.cancel()
            query().collect { send(DataState.DataBase(it, t.message!!)) }

        }
    } else {
        query().collect { send(DataState.Data(it)) }
    }
}