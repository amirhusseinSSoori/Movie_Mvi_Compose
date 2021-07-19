package com.example.movie_mvi_compose.data.db

import androidx.room.*
import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.ui.details.DetailsContract

import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(details: MovieEntity): Long

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovie(): Flow<List<MovieEntity>>
}