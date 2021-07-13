package com.example.movie_mvi_compose.data.repository

import com.example.movie_mvi_compose.data.network.errorHandling.Resource
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.source.RemoteSource
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(var network:RemoteSource) {

   suspend fun getMovie(): Resource<MovieResponse> {
        return  network.remoteAllMovie()
    }
}