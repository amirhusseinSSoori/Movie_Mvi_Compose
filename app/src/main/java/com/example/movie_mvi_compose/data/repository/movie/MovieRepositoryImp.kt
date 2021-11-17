package com.example.movie_mvi_compose.data.repository.movie

import com.dropbox.android.external.store4.*
import com.example.movie_mvi_compose.data.db.MyDataBase
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.mapper.MoviesMapper
import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import com.example.movie_mvi_compose.ui.base.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    var network: RemoteSource,
    var local: LocalSource,
    var mapper: MoviesMapper,
    var db: MyDataBase,
     val dispatcher: DispatcherProvider
) : MovieRepository {

    @FlowPreview
    private val store: Store<String, List<MovieEntity>> = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            network.remoteAllMovie()
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = { key -> local.allMovie() },
            writer = { key: String, input: MovieResponse ->
                val latestNews = mapper.mapFromEntityList(input)
                local.db.update(latestNews)
            }
        )
    ).build()

    override suspend fun getLatestNews(): Flow<Result<List<MovieItem>>> {
        return flow {
            store.stream(StoreRequest.cached(key = "latest_news", refresh = true))
                .flowOn(dispatcher.io())
                .collect { response: StoreResponse<List<MovieEntity>> ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            emit(Result.loading<List<MovieItem>>())
                        }
                        is StoreResponse.Error -> {
                            emit(Result.error<List<MovieItem>>())
                        }
                        is StoreResponse.Data -> {
                            emit(Result.success(mapper.mapToEntityList(response.value)))
                        }
                        is StoreResponse.NoNewData -> emit(Result.success(emptyList<MovieItem>()))
                    }
                }
        }.flowOn(dispatcher.io())
    }

}


interface DispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class DispatcherProviderImpl : DispatcherProvider
