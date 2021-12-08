package com.example.movie_mvi_compose.data.repository.details

import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.ui.base.DataState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DetailsRepository {
    fun getSummery(id:Int): Flow<Result<Response<MovieDetials>>>
}