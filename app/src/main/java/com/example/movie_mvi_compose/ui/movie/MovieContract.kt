package com.example.movie_mvi_compose.ui.movie

import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.ui.base.UiEffect
import com.example.movie_mvi_compose.ui.base.UiEvent
import com.example.movie_mvi_compose.ui.base.UiState

class MovieContract {
    sealed class Event : UiEvent {
        object ShowMovie : Event()
    }


    data class State(
        val state: MovieState
    ) : UiState


    sealed class MovieState {
        object Idle : MovieState()
        data class Success(var Movie: MovieResponse) : MovieState()
    }


    sealed class Effect : UiEffect {
        object Empty : MovieState()
        data class ShowError(val message:String) : Effect()
    }
}