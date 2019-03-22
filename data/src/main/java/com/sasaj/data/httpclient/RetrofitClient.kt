package com.sasaj.data.httpclient

import com.sasaj.data.entities.Location
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    private val retrofit: Retrofit
    private val client: OkHttpClient
    private val service: OWMService

    init {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        retrofit = Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        service = retrofit.create<OWMService>(OWMService::class.java)
    }

    private fun getService(): OWMService {
        return service
    }

    fun getWeather(cityId: Int, units: String = "metric", lang: String = "en", apiKey: String = API_KEY): Single<Location> {
        return getService().getWeatherForCityId(cityId, units, lang, apiKey)
    }

    companion object {
        const val API_URL = "http://api.openweathermap.org/"
        const val API_KEY = "c3bf51c56cba5ad1582ef63851aee28d"
    }
}