package com.sasaj.data

import android.content.Context
import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.entities.City
import io.reactivex.Observable

class WeatherRepositoryImpl(val context: Context) : WeatherRepository {

    override fun getCities(): Observable<List<City>> {
        val localRepo = LocalRepository(context)
        val citiesObservable : Observable<List<City>> = localRepo.getCities()
        return citiesObservable
    }
}