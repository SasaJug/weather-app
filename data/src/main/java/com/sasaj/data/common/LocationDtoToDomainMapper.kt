package com.sasaj.data.common

import com.sasaj.data.entities.Location
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.Weather

class LocationDtoToDomainMapper : Mapper<Location, Weather>() {
    override fun mapFrom(from: Location): Weather {
        return Weather(
                from.weather[0].main,
                from.weather[0].description,
                from.main.temp,
                from.wind.speed,
                from.main.humidity,
                from.main.pressure,
                "http://openweathermap.org/img/w/"+from.weather[0].icon+".png"
        )
    }
}