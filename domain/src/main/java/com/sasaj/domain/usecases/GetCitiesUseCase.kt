package com.sasaj.domain.usecases

import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.City
import io.reactivex.Observable

class GetCitiesUseCase(transformer: Transformer<List<City>>,
                       val weatherRepo  : WeatherRepository) : UseCase<List<City>>(transformer){


    fun getCities() : Observable<List<City>> {
        return observable()
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<City>> {
        return weatherRepo.getCities()
    }
}