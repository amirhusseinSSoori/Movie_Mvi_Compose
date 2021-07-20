package com.example.movie_mvi_compose.ui.movie

import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.repository.movie.MovieRepositoryIml
import com.example.movie_mvi_compose.ui.base.BaseViewModel
import com.example.movie_mvi_compose.ui.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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
            repository.getAllMovie().collect {
                when (it) {
                    is Resource.Success -> {
                        setState { copy(state = MovieContract.MovieState.Movie(list = it)) }
                    }
                    is Resource.Error -> {
                        setState { copy(state = MovieContract.MovieState.Movie(list = it)) }
                        setEffect {
                            MovieContract.Effect.ShowError(
                                it.error!!.message!!,
                                list = it.data!!
                            )
                        }
                    }
                    else -> Unit
                }
            }

        }


    }
}
