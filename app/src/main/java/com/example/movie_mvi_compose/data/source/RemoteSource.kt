package com.example.movie_mvi_compose.data.source

import com.example.movie_mvi_compose.data.network.Api.MovieClient

import javax.inject.Inject

class RemoteSource @Inject constructor(var api:MovieClient) {
   suspend fun remoteAllMovie() = api.movies()


   suspend fun remoteDetailsMovie(id:Int)= api.movieDetails(id)
}