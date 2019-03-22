package com.sasaj.data.mappers

import com.sasaj.data.entities.CityDto
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.City
import com.sasaj.domain.entities.Coord

class CityDtoToDomainMapper() : Mapper<CityDto, City>() {
    override fun mapFrom(from: CityDto): City {
        val coord= Coord(from.coordDto.lat, from.coordDto.lon)
        return City(coord,
                from.country,
                from.id,
                from.name)
    }
}