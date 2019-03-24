package com.sasaj.data.httpclient

import com.sasaj.data.entities.LocationDto
import io.reactivex.Single

open class RetrofitClient (private val service : OWMService, private val API_KEY : String){


    fun getWeather(cityId: Int, units: String = "metric", lang: String = "en"): Single<LocationDto> {
        return service.getWeatherForCityId(cityId, units, lang, API_KEY)
    }
}