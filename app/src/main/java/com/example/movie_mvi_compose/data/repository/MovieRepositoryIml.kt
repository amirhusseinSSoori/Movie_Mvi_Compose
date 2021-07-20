package com.example.movie_mvi_compose.data.repository

import androidx.room.withTransaction
import com.example.movie_mvi_compose.ui.base.networkBoundResource
import com.example.movie_mvi_compose.data.db.MyDataBase
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.mapper.CheckStatusMapper
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryIml @Inject constructor(
    var network: RemoteSource,
    var local: LocalSource,
    var mapper: CheckStatusMapper,
    var db:MyDataBase
) :
    MovieRepository {

    override suspend fun getMovie() = network.remoteAllMovie()
    override suspend fun getDetailsMovie(id: Int): MovieDetials = network.remoteDetailsMovie(id)
    override  fun getAllMovie(): Flow<List<MovieEntity>> {
        return local.allMovie()
    }

    fun getRestaurants() = networkBoundResource(
        query = {
            local.allMovie()
        },
        fetch = {


            network.remoteAllMovie()
        },
        saveFetchResult = { restaurants ->
            db.withTransaction {
                local.delete()
                local.insertMovie(mapper.mapFromEntityList(restaurants))
            }
        }
    )
}