package com.sasaj.data

import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.entities.City
import com.sasaj.domain.entities.Weather
import io.reactivex.Observable

class WeatherRepositoryImpl(private val localRepository: LocalRepository,
                            private val remoteRepository: RemoteRepository) : WeatherRepository {


    override fun getCities(): Observable<List<City>> {
        return localRepository.getCities()
    }

    override fun getWeather(cityId : Int): Observable<Weather> {
        return remoteRepository.getWeather(cityId)
    }
}