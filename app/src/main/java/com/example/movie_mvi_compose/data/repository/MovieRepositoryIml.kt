package com.example.movie_mvi_compose.data.repository

import com.example.movie_mvi_compose.data.network.errorHandling.Resource
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.source.RemoteSource
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryIml @Inject constructor(var network:RemoteSource) :MovieRepository{
    override suspend fun getMovie() = network.remoteAllMovie()
    override suspend fun getDetailsMovie(id:Int): MovieDetials =network.remoteDetailsMovie(id)
}