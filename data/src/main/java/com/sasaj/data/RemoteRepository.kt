package com.sasaj.data

import com.sasaj.data.common.LocationDtoToDomainMapper
import com.sasaj.data.httpclient.RetrofitClient
import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.entities.City
import com.sasaj.domain.entities.Weather
import io.reactivex.Observable

class RemoteRepository(private val httpClient: RetrofitClient, private val locationDtoToDomainMapper: LocationDtoToDomainMapper) : WeatherRepository {

    override fun getCities(): Observable<List<City>> {
        TODO("Implement fetching locations from remote service.") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeather(cityId: Int): Observable<Weather> {
        return httpClient.getWeather(cityId).map { location -> locationDtoToDomainMapper.mapFrom(location) }.toObservable()
    }
}