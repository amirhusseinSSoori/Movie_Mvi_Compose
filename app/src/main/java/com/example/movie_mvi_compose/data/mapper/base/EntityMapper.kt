package com.example.movie_mvi_compose.data.mapper.base

interface EntityMapper<EntityMapper,DomainModel> {

    fun mapFromEntity(entity: EntityMapper): DomainModel
    fun mapToEntity(domainModel: DomainModel): EntityMapper
    fun mapFromEntityList(entities: List<EntityMapper>): List<DomainModel>
    fun mapToEntityList(domains: List<DomainModel>): List<EntityMapper>

}