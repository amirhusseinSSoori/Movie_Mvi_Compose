package com.example.movie_mvi_compose.data.db

import androidx.room.Insert
import androidx.room.*
import com.example.movie_mvi_compose.data.db.entity.MovieEntity

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: List<T>)

    @Delete
    suspend fun delete(obj: List<T>)
}