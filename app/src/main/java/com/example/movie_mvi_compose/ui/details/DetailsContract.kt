package com.example.movie_mvi_compose.ui.details

import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.ui.base.UiEffect
import com.example.movie_mvi_compose.ui.base.UiEvent
import com.example.movie_mvi_compose.ui.base.UiState

class DetailsContract {

    sealed class Event : UiEvent {
        data class ShowDetails (var id:Int): Event()
    }


    data class State(
        val state: DetailsState
    ) : UiState


    sealed class DetailsState {
        object Idle : DetailsState()
        data class Success(var details: MovieDetials) : DetailsState()
    }


    sealed class Effect : UiEffect {
        object Empty : DetailsState()
        data class ShowError(val message:String) : Effect()
    }
}