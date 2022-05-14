package com.example.movie_mvi_compose.data.db

import androidx.room.Insert
import androidx.room.*
import com.example.movie_mvi_compose.data.db.entity.MovieEntity

@Dao
interface BaseDao<T> {
    @Insert
    suspend fun insert(obj: List<T>)
}