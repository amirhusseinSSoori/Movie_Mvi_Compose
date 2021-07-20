package com.example.movie_mvi_compose.data.source

import android.telecom.Call
import com.example.movie_mvi_compose.data.db.MovieDao
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.ui.details.DetailsContract
import javax.inject.Inject

class LocalSource @Inject  constructor( val db:MovieDao){
    suspend fun insertMovie(details: List<MovieEntity>) = db .insertDetails(details)
     fun allMovie() = db .getAllMovie()


    suspend fun delete()= db.deleteAllRestaurants()

}