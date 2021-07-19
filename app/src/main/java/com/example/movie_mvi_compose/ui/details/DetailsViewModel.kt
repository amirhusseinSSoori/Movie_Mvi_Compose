package com.example.movie_mvi_compose.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.repository.MovieRepositoryIml
import com.example.movie_mvi_compose.ui.base.BaseViewModel
import com.example.movie_mvi_compose.ui.movie.MovieContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(var repository: MovieRepositoryIml) :
    BaseViewModel<DetailsContract.Event, DetailsContract.State, DetailsContract.Effect>() {
    override fun createInitialState(): DetailsContract.State {
        return DetailsContract.State(
            DetailsContract.DetailsState.Idle
        )
    }

    override fun handleEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.ShowDetails -> {
                showDetails(event.id)
            }
        }
    }

    private fun showDetails(id: Int) {
        viewModelScope.launch {
            runCatching {
                repository.getDetailsMovie(id)
            }.onSuccess {
                setState { copy(state = DetailsContract.DetailsState.Success(details = it)) }
            }.onFailure {
                Log.e("handleError", "onFailure: ${it.message}")
                setEffect { DetailsContract.Effect.ShowError(it.message!!) }
            }.recover {
                Log.e("handleError", "onFailure: ${it.message}")
                setEffect { DetailsContract.Effect.ShowError(it.message!!) }
                return@recover "STATUS_UNKNOWN"
            }.getOrNull()

        }
    }

}