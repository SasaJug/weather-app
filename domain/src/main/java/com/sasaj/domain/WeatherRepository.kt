package com.sasaj.domain

import com.sasaj.domain.entities.City
import com.sasaj.domain.entities.Weather
import io.reactivex.Observable

interface WeatherRepository {

    fun getCities() : Observable<List<City>>
    fun getWeather(cityId : Int) : Observable<Weather>
}