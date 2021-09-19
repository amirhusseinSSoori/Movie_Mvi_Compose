package com.example.movie_mvi_compose.data.repository.details

import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.source.RemoteSource
import com.example.movie_mvi_compose.ui.base.DataState
import com.example.movie_mvi_compose.ui.base.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepositoryImp @Inject constructor(var network: RemoteSource) :DetailsRepository {
    override fun getSummery(id:Int): Flow<DataState<MovieDetials>> = flow {
        runCatching {
            network.remoteDetailsMovie(id)
        }.onSuccess {
            emit(DataState.Data(it))
        }.onFailure {
            emit(
                DataState.Response<MovieDetials>(
                    uiComponent = UIComponent.ErrorConnection(
                        message = it.message ?: "Unknown error"
                    )
                )
            )
        }
    }
}