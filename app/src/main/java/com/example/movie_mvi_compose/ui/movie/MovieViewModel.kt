package com.example.movie_mvi_compose.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.repository.MovieRepository
import com.example.movie_mvi_compose.data.network.errorHandling.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(var repository: MovieRepository ):ViewModel() {


    var getDetails= MutableStateFlow<Resource<MovieResponse>>(Resource.Empty())
    var getDetailsCollect:StateFlow<Resource<MovieResponse>> =  getDetails

    fun showAllMovie(){
        viewModelScope.launch {
            getDetails.value= repository.getAllMovie()
        }
    }


}