package com.example.movie_mvi_compose.data.repository.movie

import com.dropbox.android.external.store4.*
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class MovieRepositoryImp (
    private val network: RemoteSource,
    private val local: LocalSource,
    ) : MovieRepository {
    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun getStore(): Store<String, List<MovieEntity>> = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            network.remoteAllMovies()
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = { local.allMovies() },
            writer = { _, input: MovieResponse ->
                local.updateAllMovies(input)
            }
        )
    ).build()



}





interface DispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
}
class DispatcherProviderImpl : DispatcherProvider
