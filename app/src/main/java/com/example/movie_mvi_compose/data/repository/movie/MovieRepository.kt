package com.example.movie_mvi_compose.data.repository.movie

import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.ui.base.Result

import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getLatestNews(): Flow<Result<List<MovieItem>>>
}