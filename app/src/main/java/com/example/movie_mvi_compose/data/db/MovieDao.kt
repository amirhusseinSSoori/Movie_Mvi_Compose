package com.example.movie_mvi_compose.data.db

import androidx.room.*
import com.example.movie_mvi_compose.data.db.entity.MovieEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(details: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAllMovies()

    @Query("DELETE  FROM MovieEntity")
    suspend fun deleteAll()
    @Transaction
    suspend fun update(news: List<MovieEntity>) {
        deleteAll()
        insert(news)
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<MovieEntity>)



}