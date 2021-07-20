package com.example.movie_mvi_compose.data.mapper



import com.example.movie_mvi_compose.data.db.entity.MovieEntity
import com.example.movie_mvi_compose.data.mapper.base.EntityMapper
import com.example.movie_mvi_compose.data.network.response.MovieItem

import javax.inject.Inject

class CheckStatusMapper @Inject constructor() : EntityMapper<MovieItem, MovieEntity> {

    override fun mapFromEntity(entity: MovieItem): MovieEntity {
        return MovieEntity(
            idMovie = entity.id,
            poster = entity.poster,
            )
    }

    override fun mapToEntity(domainModel: MovieEntity): MovieItem {
        return MovieItem(
            id = domainModel.idMovie,
            poster = domainModel.poster,
            )
    }

    override fun mapFromEntityList(entities: List<MovieItem>): List<MovieEntity> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapToEntityList(domains: List<MovieEntity>): List<MovieItem> {
        return domains.map { mapToEntity(it) }
    }
}