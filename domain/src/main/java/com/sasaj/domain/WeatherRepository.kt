package com.sasaj.domain

import com.sasaj.domain.entities.City
import io.reactivex.Observable

interface WeatherRepository {

    fun getCities() : Observable<List<City>>

}