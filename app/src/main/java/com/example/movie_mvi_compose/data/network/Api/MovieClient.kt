package com.example.movie_mvi_compose.data.network.Api

import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieClient {
    companion object {
        private const val PARAM_MOVIE_ID = "movieId"
        private const val MOVIES = "movies.json"
        private const val MOVIE_DETAILS = "movie_0{$PARAM_MOVIE_ID}.json"
    }

    @GET(MOVIES)
    suspend fun callMovies(): MovieResponse

    @GET(MOVIE_DETAILS)
    suspend fun showMovieDetails(@Path(PARAM_MOVIE_ID) movieId: Int) :  Response<MovieDetials>

}