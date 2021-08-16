package com.example.movie_mvi_compose.data.repository.movie

import androidx.room.withTransaction
import com.example.movie_mvi_compose.data.db.MyDataBase
import com.example.movie_mvi_compose.data.mapper.MoviesMapper
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import com.example.movie_mvi_compose.ui.base.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryIml @Inject constructor(
    var network: RemoteSource,
    var local: LocalSource,
    var mapper: MoviesMapper,
    var db: MyDataBase
) : MovieRepository {
    override suspend fun getMovie() = network.remoteAllMovie()

    fun getSummery(id:Int): Flow<DataState<MovieDetials>> = flow {
        emit(DataState.Progress(ProgressBarState.Loading))
        runCatching {
            network.remoteDetailsMovie(id)
        }.onSuccess {
            emit(DataState.Progress(ProgressBarState.Idle))
            emit(DataState.Data(it))
        }.onFailure {
            emit(DataState.Progress(ProgressBarState.Idle))
            emit(
                DataState.Response<MovieDetials>(
                    uiComponent = UIComponent.ErrorConnection(
                        message = it.message ?: "Unknown error"
                    )
                )
            )
        }
    }



    fun getAllMovie() = networkBoundResource(
        query = {
            local.allMovie()
        },
        fetch = {
            delay(2000)
            network.remoteAllMovie()
        },
        saveFetchResult = { data ->
            db.withTransaction {
                local.delete()
                local.insertMovie(mapper.mapFromEntityList(data))
            }
        }
    )


}