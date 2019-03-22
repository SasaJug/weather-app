package com.sasaj.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sasaj.data.entities.CityDto
import com.sasaj.data.mappers.CityDtoToDomainMapper
import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.entities.City
import com.sasaj.domain.entities.Weather
import io.reactivex.Observable


class LocalRepository(val context: Context, private val cityDtoToDomainMapper: CityDtoToDomainMapper) : WeatherRepository{

    override fun getCities(): Observable<List<City>> {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<CityDto>>() {}.type
        val cityDtos: List<CityDto> = gson.fromJson(cities, listType)
        return Observable.fromIterable(cityDtos).map { cityDtoToDomainMapper.mapFrom(it) }.toList().toObservable()
    }

    override fun getWeather(cityId: Int): Observable<Weather> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}