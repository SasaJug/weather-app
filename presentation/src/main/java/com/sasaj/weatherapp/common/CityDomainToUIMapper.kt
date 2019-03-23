package com.sasaj.weatherapp.common

import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.City
import com.sasaj.weatherapp.entities.CityUI
import com.sasaj.weatherapp.entities.CoordUI

class CityDomainToUIMapper : Mapper<City, CityUI>(){
    override fun mapFrom(from: City): CityUI {
        val coordUI = CoordUI(from.coord.lat, from.coord.lon)
        return CityUI(
                coordUI,
                from.country,
                from.id,
                from.name
        )
    }

    fun mapFromList(list : List<City>){
        list.forEach{city -> mapFrom(city)}
    }
}