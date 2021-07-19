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

    var mutebale = MutableStateFlow<NewStatus>(NewStatus.Empty)
    var state: StateFlow<NewStatus> = mutebale


    fun showDetails(id:Int){
        viewModelScope.launch {
            runCatching {
                repository.getDetailsMovie(id)

            }.onSuccess {
                mutebale.value = NewStatus.CheckMessage(it)
            }.onFailure {
                Log.e("handelErrorDetails", "onFailure: ${it.message}", )

            }.recover {
                Log.e("handelErrorDetails", "onRecover: ${it.message}", )

                return@recover  "STATUS_UNKNOWN"
            }

        }
    }


    sealed class NewStatus(){
        object Empty : NewStatus()
        class  CheckMessage(var message: MovieDetials) : NewStatus()

    }
}