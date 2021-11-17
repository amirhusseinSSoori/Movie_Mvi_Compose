package com.example.movie_mvi_compose.data.repository.movie

import com.dropbox.android.external.store4.Store
import com.example.movie_mvi_compose.data.db.entity.MovieEntity


interface MovieRepository {
    fun getStore() : Store<String, List<MovieEntity>>
}