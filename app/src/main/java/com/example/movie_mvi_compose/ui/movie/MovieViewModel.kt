package com.example.movie_mvi_compose.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.mapper.MoviesMapper
import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.data.repository.movie.DispatcherProvider
import com.example.movie_mvi_compose.data.repository.movie.MovieRepository
import com.example.movie_mvi_compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    var repository: MovieRepository,
    val dispatcher: DispatcherProvider,
    var mapper: MoviesMapper,
) : ViewModel() {

    val stateMovie = MutableStateFlow(StateList())
    val _stateMovie = stateMovie.asStateFlow()

    init {
        viewModelScope.launch {
            getLatestMovie()
        }
    }


    private suspend fun getLatestMovie() {
        repository.getStore().stream(StoreRequest.cached(key = "item", refresh = true))
            .flowOn(dispatcher.io())
            .collect { response: StoreResponse<List<MovieEntity>> ->
                when (response) {
                    is StoreResponse.Loading -> {
                        stateMovie.value = stateMovie.value.copy(loading = true)
                    }
                    is StoreResponse.Error -> {
                        stateMovie.value = stateMovie.value.copy(message = "error Connection")
                    }
                    is StoreResponse.Data -> {
                        stateMovie.value = stateMovie.value.copy(loading = false)
                        stateMovie.value =
                            stateMovie.value.copy(details = mapper.mapToEntityList(response.value))
                    }
                    else -> Unit
                }
            }
    }


    data class StateList(
        var loading: Boolean = false,
        var message: String = " ",
        var details: List<MovieItem>? = emptyList(),
    )

}
