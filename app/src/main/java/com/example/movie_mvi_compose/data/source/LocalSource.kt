package com.example.movie_mvi_compose.data.source

import com.comexample.moviemvicompose.MovieEntityQueries
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSource @Inject constructor(
    val movieEntityQueries: MovieEntityQueries,
) {
    fun allMovies() = movieEntityQueries.selectAll().asFlow().map { to ->
        to.executeAsList()
    }

    fun updateAllMovies(movies: MovieResponse) {
        movieEntityQueries.delete()
        movies.forEach {n->
            movieEntityQueries.insert(idMovie = n.id ?: "", poster = n.poster ?: "")
        }
    }
}