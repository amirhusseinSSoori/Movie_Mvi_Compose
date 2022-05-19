package com.example.movie_mvi_compose.data.source

import com.example.movie_mvi_compose.data.network.Api.MovieClient

import javax.inject.Inject

class RemoteSource @Inject constructor(var api: MovieClient) {
    suspend fun remoteAllMovies() = api.callMovies()
    suspend fun remoteDetailsMovies(id: Int) = api.showMovieDetails(id)
}