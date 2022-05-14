package com.example.movie_mvi_compose.data.repository.movie

import com.comexample.moviemvicompose.MovieEntity
import com.dropbox.android.external.store4.Store



interface MovieRepository {
    fun getStore() : Store<String, List<MovieEntity>>
}