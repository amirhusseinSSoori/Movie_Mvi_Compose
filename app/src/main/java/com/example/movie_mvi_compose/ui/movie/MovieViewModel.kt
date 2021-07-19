package com.example.movie_mvi_compose.ui.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.repository.MovieRepositoryIml
import com.example.movie_mvi_compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(var repository: MovieRepositoryIml) :
    BaseViewModel<MovieContract.Event, MovieContract.State, MovieContract.Effect>() {

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
            runCatching {
                repository.getMovie()
            }.onSuccess {
                setState { copy(state = MovieContract.MovieState.Success(Movie = it)) }
            }.onFailure {
                Log.e("handleError", "onFailure: ${it.message}")
                setEffect { MovieContract.Effect.ShowError(it.message!!) }
            }.recover {
                setEffect { MovieContract.Effect.ShowError(it.message!!) }
                return@recover "STATUS_UNKNOWN"
            }

        }

    }
}