package com.example.movie_mvi_compose.data.repository.movie

import androidx.room.withTransaction
import com.example.movie_mvi_compose.data.db.MyDataBase
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.mapper.MoviesMapper
import com.example.movie_mvi_compose.data.network.response.MovieDetials
import com.example.movie_mvi_compose.data.source.LocalSource
import com.example.movie_mvi_compose.data.source.RemoteSource
import com.example.movie_mvi_compose.ui.base.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    var network: RemoteSource,
    var local: LocalSource,
    var mapper: MoviesMapper,
    var db: MyDataBase
) : MovieRepository {


   @InternalCoroutinesApi
   override fun getAllMovie(): Flow<DataState<List<MovieEntity>>> {
      return networkBoundResource(
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

}