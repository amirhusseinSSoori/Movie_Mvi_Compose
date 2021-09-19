package com.example.movie_mvi_compose.ui.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.repository.details.DetailsRepository
import com.example.movie_mvi_compose.data.repository.movie.MovieRepository
import com.example.movie_mvi_compose.ui.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    var repository: DetailsRepository,
    private val savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<DetailsContract.Event, DetailsContract.State, DetailsContract.Effect>() {


    init {
        savedStateHandle.get<Int>("details")?.let { heroId ->
            showDetails(heroId)
        }
    }


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
        repository.getSummery(id).onEach { data ->
            when (data) {
                is DataState.Data -> {
                    setState { copy(state = DetailsContract.DetailsState.Success(details = data.data!!)) }
                }
                is DataState.Response -> {
                    if (data.uiComponent is UIComponent.ErrorConnection) {
                        setEffect {
                            DetailsContract.Effect.ShowError((data.uiComponent).message)
                        }
                    }
                }

            }
        }.launchIn(viewModelScope)

    }
}