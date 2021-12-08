package com.example.movie_mvi_compose.data.repository.details

import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.source.RemoteSource
import com.example.movie_mvi_compose.ui.base.DataState
import com.example.movie_mvi_compose.ui.base.UIComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import retrofit2.Response
import javax.inject.Inject

class DetailsRepositoryImp (var network: RemoteSource) :DetailsRepository {
    override fun getSummery(id:Int): Flow<Result<Response<MovieDetials>>> = flow {
        emit(Result.success(network.remoteDetailsMovies(id)))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(IO)
}