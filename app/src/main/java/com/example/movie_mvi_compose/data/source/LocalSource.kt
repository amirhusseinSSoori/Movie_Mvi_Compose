package com.example.movie_mvi_compose.data.source

import com.example.movie_mvi_compose.data.db.MovieDao
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.mapper.MoviesMapper
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import javax.inject.Inject

class LocalSource @Inject constructor(val db: MovieDao, val mapper: MoviesMapper) {
    fun allMovies() = db.getAllMovie()
    suspend fun updateAllMovies(movies: MovieResponse) = db.update(mapper.mapFromEntityList(movies))
}