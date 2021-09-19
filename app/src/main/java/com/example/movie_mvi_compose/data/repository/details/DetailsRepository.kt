package com.example.movie_mvi_compose.data.repository.details

import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.ui.base.DataState
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getSummery(id:Int): Flow<DataState<MovieDetials>>
}