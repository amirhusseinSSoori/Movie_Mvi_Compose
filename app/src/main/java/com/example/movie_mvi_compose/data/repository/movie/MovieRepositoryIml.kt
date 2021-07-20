package com.example.movie_mvi_compose.data.repository.movie

import androidx.room.withTransaction
import com.example.movie_mvi_compose.ui.base.networkBoundResource
import com.example.movie_mvi_compose.data.db.MyDataBase
import com.example.movie_mvi_compose.data.mapper.MoviesMapper
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.repository.movie.MovieRepository
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class MovieRepositoryIml @Inject constructor(
    var network: RemoteSource,
    var local: LocalSource,
    var mapper: MoviesMapper,
    var db:MyDataBase
) : MovieRepository {
    override suspend fun getMovie() = network.remoteAllMovie()
    override suspend fun getDetailsMovie(id: Int): MovieDetials = network.remoteDetailsMovie(id)
    fun getAllMovie() = networkBoundResource(
        query = {
            local.allMovie()
        },
        fetch = {
            delay(2000)
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