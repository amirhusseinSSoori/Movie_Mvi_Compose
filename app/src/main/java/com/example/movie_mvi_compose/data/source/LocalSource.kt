package com.example.movie_mvi_compose.data.source

import com.example.movie_mvi_compose.data.db.MovieDao
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import javax.inject.Inject

class LocalSource @Inject constructor(val db: MovieDao) {
    fun allMovie() = db.getAllMovie()
}