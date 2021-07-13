package com.example.movie_mvi_compose.ui.movie

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import com.example.movie_mvi_compose.data.repository.MovieRepository
import com.example.movie_mvi_compose.data.network.errorHandling.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(var repository: MovieRepository ):ViewModel() {


    var mutebale= MutableStateFlow(MovieResponse())
    var state:StateFlow<MovieResponse> = mutebale

    var handelError= mutableStateOf("")
    var isLoading= mutableStateOf(false)



    fun showAllMovie(){
        viewModelScope.launch {

            val res = repository.getMovie()
            when(res){
                is Resource.Success -> {
                    handelError.value = ""
                    isLoading.value=true
                    mutebale.value=res.data!!
                }
                is Resource.Error ->{
                    delay(1000)
                    handelError.value = res.message!!
                    isLoading.value=true
                }

                else -> Unit
            }
        }




    }

}