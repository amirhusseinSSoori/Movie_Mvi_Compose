package com.example.movie_mvi_compose.ui.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comexample.moviemvicompose.MovieEntity
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.example.movie_mvi_compose.data.network.response.MovieItem

import com.example.movie_mvi_compose.data.repository.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val movieState = MutableStateFlow(StateMovie())
    val _movieState = movieState.asStateFlow()

    init {
        viewModelScope.launch {
            getLatestMovie()
        }
    }

    private suspend fun getLatestMovie() {
        repository.getStore().stream(StoreRequest.cached(key = "item", refresh = true))
            .flowOn(Dispatchers.IO)
            .collect { response: StoreResponse<List<MovieEntity>> ->
                when (response) {
                    is StoreResponse.Loading -> {
                        movieState.value = movieState.value.copy(loading = true)
                    }
                    is StoreResponse.Error -> {
                        movieState.value = movieState.value.copy(message = "error Connection")
                        movieState.value = movieState.value.copy(loading = false)
                    }
                    is StoreResponse.Data -> {
                        movieState.value = movieState.value.copy(loading = false)
                        movieState.value =
                            movieState.value.copy(details = mapToEntityList(response.value))
                    }
                    else -> Unit
                }
            }
    }


    data class StateMovie(
        var loading: Boolean = true,
        var message: String = " ",
        var details: List<MovieItem>? = emptyList(),
    )
     fun mapToEntityList(domains: List<MovieEntity>): List<MovieItem> {
        return domains.map { mapToEntity(it) }
    }



    fun mapToEntity(domainModel: MovieEntity): MovieItem {
        return MovieItem(
            id = domainModel.idMovie,
            poster = domainModel.poster,
        )
    }
}
