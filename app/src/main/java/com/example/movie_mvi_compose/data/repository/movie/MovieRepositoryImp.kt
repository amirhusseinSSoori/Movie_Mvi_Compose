package com.example.movie_mvi_compose.data.repository.movie

import android.util.Log
import com.comexample.moviemvicompose.MovieEntity
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class MovieRepositoryImp (
    private val network: RemoteSource,
    private val local: LocalSource,
    ) : MovieRepository {

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun getStore(): Store<String, List<MovieEntity>> = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            network.remoteAllMovies()
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = {
                local.allMovies()
                },
            writer = { _, input: MovieResponse ->
                local.updateAllMovies(input)
            }
        )
    ).build()



}






