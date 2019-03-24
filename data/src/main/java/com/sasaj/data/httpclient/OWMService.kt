package com.sasaj.data.httpclient

import com.sasaj.data.entities.LocationDto
import com.sasaj.domain.entities.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface OWMService {

    @GET("data/2.5/weather")
    fun getWeatherForCityId(@Query("id") cityId: Int,
                            @Query("units") units: String,
                            @Query("lang") language: String,
                            @Query("appid") apiKey : String): Single<LocationDto>
}