package com.example.movie_mvi_compose.data.network.Api

import com.example.movie_mvi_compose.data.network.response.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MovieClient {
    companion object {
        private const val PARAM_MOVIE_ID = "movieId"
        private const val MOVIES = "movies.json"
        private const val MOVIE_DETAILS = "movie_0{$PARAM_MOVIE_ID}.json"
    }

    @GET("movies.json")
    suspend fun movies(): Response<MovieResponse>
}