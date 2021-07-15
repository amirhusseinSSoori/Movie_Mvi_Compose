package com.example.movie_mvi_compose.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.repository.MovieRepositoryIml
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(var repository: MovieRepositoryIml) : ViewModel() {

    var mutebale = MutableStateFlow(MovieDetials())
    var state: StateFlow<MovieDetials> = mutebale


    fun showDetails(id:Int){
        viewModelScope.launch {
            runCatching {
                repository.getDetailsMovie(id)

            }.onSuccess {

                mutebale.value = it
            }.onFailure {
                Log.e("handelError", "onFailure: ${it.message}", )

            }.recover {
                Log.e("handelError", "onRecover: ${it.message}", )

                return@recover  "STATUS_UNKNOWN"
            }

        }
    }
}