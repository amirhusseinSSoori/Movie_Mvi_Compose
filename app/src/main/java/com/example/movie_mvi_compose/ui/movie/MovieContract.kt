package com.example.movie_mvi_compose.ui.movie

import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.ui.base.*
import kotlinx.coroutines.flow.Flow

class MovieContract {
    sealed class Event : UiEvent {
        object ShowMovie : Event()
    }


    data class State(
        val state: MovieState
    ) : UiState


    sealed class MovieState {
        object Idle : MovieState()
        data class Movie(var list: List<MovieEntity>): MovieState()
    }


    sealed class Effect : UiEffect {
        object Empty : MovieState()
        data class ShowError(val message:String,var list: List<MovieEntity>) : Effect()
    }
}