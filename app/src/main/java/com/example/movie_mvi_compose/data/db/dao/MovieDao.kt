package com.example.movie_mvi_compose.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.movie_mvi_compose.data.db.BaseDao
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
abstract class MovieDao: BaseDao<MovieEntity> {

    @Query("SELECT * FROM MovieEntity")
    abstract fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("DELETE  FROM MovieEntity")
    abstract suspend fun delete()

    @Transaction
    open suspend fun update(obj: List<MovieEntity>) {
        delete()
        insert(obj)
    }

}