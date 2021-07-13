package com.example.movie_mvi_compose.data.source

import com.example.movie_mvi_compose.data.network.Api.MovieClient
import com.example.movie_mvi_compose.data.network.errorHandling.Resource
import com.example.movie_mvi_compose.data.network.errorHandling.SafeApi
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import retrofit2.Response

import javax.inject.Inject

class RemoteSource @Inject constructor(var api:MovieClient): SafeApi() {

   suspend fun remoteAllMovie(): Resource<MovieResponse> = safeApi { api.movies() }


}