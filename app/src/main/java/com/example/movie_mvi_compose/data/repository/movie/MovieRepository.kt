package com.example.movie_mvi_compose.data.repository.movie

import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    //server
    suspend fun getMovie():MovieResponse
    suspend fun getDetailsMovie(id:Int):MovieDetials
    //dataBase

//    suspend fun getAllMovie():Flow<Any>


}