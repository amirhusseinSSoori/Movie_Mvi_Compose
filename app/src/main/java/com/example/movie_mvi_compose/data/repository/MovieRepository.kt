package com.example.movie_mvi_compose.data.repository

import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.network.response.MovieResponse

interface MovieRepository {
    suspend fun getMovie():MovieResponse
    suspend fun getDetailsMovie(id:Int):MovieDetials
}