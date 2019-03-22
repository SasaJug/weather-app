package com.sasaj.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sasaj.data.entities.CityDto
import com.sasaj.data.mappers.CityDtoToDomainMapper
import com.sasaj.domain.entities.City
import io.reactivex.Observable


class LocalRepository(val context: Context, val cityDtoToDomainMapper: CityDtoToDomainMapper) {

    fun getCities(): Observable<List<City>> {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<CityDto>>() {}.type
        val cityDtos: List<CityDto> = gson.fromJson(cities, listType)
        return Observable.fromIterable(cityDtos).map { cityDtoToDomainMapper.mapFrom(it) }.toList().toObservable()
    }
}