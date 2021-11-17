package com.example.movie_mvi_compose.ui.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.repository.movie.MovieRepository
import com.example.movie_mvi_compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(var repository: MovieRepository) :
    BaseViewModel<MovieContract.Event, MovieContract.State, MovieContract.Effect>() {
    init {
        detailsOfMovies()
    }

    override fun createInitialState(): MovieContract.State {
        return MovieContract.State(
            MovieContract.MovieState.Idle
        )
    }

    override fun handleEvent(event: MovieContract.Event) {
        when (event) {
            is MovieContract.Event.ShowMovie -> {
                detailsOfMovies()
            }
        }
    }

    private fun detailsOfMovies() {
        viewModelScope.launch {
            repository.getLatestNews().collect {
                if(it.isSuccess()){
                   setState { copy(state = MovieContract.MovieState.Movie(list = it.data!!)) }
               }
            }
        }
    }
}
