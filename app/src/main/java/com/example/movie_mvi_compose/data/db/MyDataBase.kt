package com.example.movie_mvi_compose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movie_mvi_compose.data.db.dao.MovieDao
import com.example.movie_mvi_compose.data.db.entity.MovieEntity


@Database(
    entities = [MovieEntity::class],
    version =1
)
abstract class MyDataBase : RoomDatabase()
{
    abstract fun getMyDao(): MovieDao
}