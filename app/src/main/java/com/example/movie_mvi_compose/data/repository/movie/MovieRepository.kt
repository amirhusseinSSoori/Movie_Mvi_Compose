package com.example.movie_mvi_compose.data.repository.movie

import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.ui.base.DataState
import com.example.movie_mvi_compose.ui.base.networkBoundResource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.internal.ChannelFlow


interface MovieRepository {

    fun getAllMovie(): Flow<DataState<List<MovieEntity>>>
}