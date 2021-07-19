package com.example.movie_mvi_compose.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntity")
data class MovieEntity(
    val idMovie: String? = null,
    val poster: String? = null,
    @PrimaryKey val id: Long? = null
)