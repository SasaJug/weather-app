package com.sasaj.data

import android.content.Context
import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.entities.City
import io.reactivex.Observable

class WeatherRepositoryImpl(private val localRepository: LocalRepository) : WeatherRepository {

    override fun getCities(): Observable<List<City>> {
        return localRepository.getCities()
    }
}