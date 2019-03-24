package com.sasaj.data.common

import com.sasaj.data.entities.LocationDto
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.Weather

class LocationDtoToDomainMapper : Mapper<LocationDto, Weather>() {
    override fun mapFrom(from: LocationDto): Weather {
        return Weather(
                from.weatherList[0].main,
                from.weatherList[0].description,
                from.mainDto.temp.toString(),
                from.windDto.speed.toString(),
                from.mainDto.humidity.toString(),
                from.mainDto.pressure.toString(),
                "http://openweathermap.org/img/w/"+from.weatherList[0].icon+".png"
        )
    }
}