package com.sasaj.domain.usecases

import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.Weather
import io.reactivex.Observable
import java.util.*

class GetWeatherForCityUseCase(transformer: Transformer<Weather>,
                               private val weatherRepo: WeatherRepository) : UseCase<Weather>(transformer) {

    fun getWeather(cityId: Int): Observable<Weather> {
        val data = HashMap<String, Int>()
        data[PARAM_CITY_ID] = cityId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Weather> {
        return weatherRepo.getWeather(data?.get(PARAM_CITY_ID) as Int)
    }

    companion object {
        private const val PARAM_CITY_ID = "param:city_id"
    }
}