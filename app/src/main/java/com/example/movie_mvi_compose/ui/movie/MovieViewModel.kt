package com.example.movie_mvi_compose.ui.movie

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.repository.MovieRepositoryIml
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(var repository: MovieRepositoryIml) : ViewModel() {


    var mutebale = MutableStateFlow(MovieResponse())
    var state: StateFlow<MovieResponse> = mutebale

    var handelError = mutableStateOf("empty")
    var isLoading = mutableStateOf(false)


    fun showAllMovie() {
        viewModelScope.launch {
            runCatching {
                repository.getMovie()

            }.onSuccess {
                handelError.value="Success"
                mutebale.value = it
            }.onFailure {
                Log.e("handelError", "onFailure: ${it.message}", )
                handelError.value="Failure"
            }.recover {
                Log.e("handelError", "onRecover: ${it.message}", )
                handelError.value="Failure"
                return@recover  "STATUS_UNKNOWN"
            }

        }


    }

}