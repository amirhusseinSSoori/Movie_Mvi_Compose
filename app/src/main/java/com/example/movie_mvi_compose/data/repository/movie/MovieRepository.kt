package com.example.movie_mvi_compose.data.repository.movie

import com.dropbox.android.external.store4.Store
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.ui.base.Result

import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    fun getStore() : Store<String, List<MovieEntity>>
//    suspend fun getLatestNews(): Flow<Result<List<MovieItem>>>
}