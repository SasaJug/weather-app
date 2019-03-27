package com.sasaj.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sasaj.data.entities.CityDto
import com.sasaj.data.mappers.CityDtoToDomainMapper
import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.entities.City
import com.sasaj.domain.entities.Weather
import io.reactivex.Observable


class LocalRepository(val context: Context, private val cityDtoToDomainMapper: CityDtoToDomainMapper) : WeatherRepository {

    private val cache: HashMap<Int, Pair<Long, Weather>> = hashMapOf()

    override fun getCities(): Observable<List<City>> {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<CityDto>>() {}.type
        val cityDtos: List<CityDto> = gson.fromJson(cities, listType)
        return Observable.fromIterable(cityDtos).map { cityDtoToDomainMapper.mapFrom(it) }.toList().toObservable()
    }

    override fun getWeather(cityId: Int): Observable<Weather> {
        val cachedWeather = cache[cityId]
        return if (cachedWeather != null) {
            Observable.just(cachedWeather)
                    .filter { cached -> System.currentTimeMillis() - cached.first < cacheValidityPeriod }
                    .map { cached -> cached.second }
                    .doOnNext{ weather -> Log.i(TAG, "From cache : $weather")}
        } else {
            Observable.empty<Weather>()
        }
    }

    fun saveWeather(cityId: Int, weather: Weather) {
        cache[cityId] = Pair(System.currentTimeMillis(), weather)
    }

    companion object {

        const val cacheValidityPeriod = 1000 * 60 * 5
        const val TAG = "LocalRepository"
    }


}