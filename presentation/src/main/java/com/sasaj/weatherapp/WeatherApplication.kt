package com.sasaj.weatherapp

import android.app.Application
import android.util.Log
import com.sasaj.graphics.drawingapp.di.AppComponent
import com.sasaj.graphics.drawingapp.di.DaggerAppComponent
import com.sasaj.weatherapp.cities.di.WeatherListModule
import com.sasaj.weatherapp.cities.di.WeatherListSubcomponent
import com.sasaj.weatherapp.citydetails.di.WeatherDetailsModule
import com.sasaj.weatherapp.citydetails.di.WeatherDetailsSubcomponent
import com.sasaj.weatherapp.di.ApplicationModule
import io.reactivex.plugins.RxJavaPlugins

class WeatherApplication : Application() {

    private var weatherListComponent: WeatherListSubcomponent? = null
    private var weatherDetailsComponent: WeatherDetailsSubcomponent? = null

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { e -> Log.e("App", e.message, e) }
        appComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }


    fun createCitiesListComponent(): WeatherListSubcomponent {
        weatherListComponent = appComponent.plus(WeatherListModule())
        return weatherListComponent!!
    }

    fun releaseCitiesListComponent() {
        weatherListComponent = null
    }

    fun createWeatherDetailsComponent(): WeatherDetailsSubcomponent {
        weatherDetailsComponent = appComponent.plus(WeatherDetailsModule())
        return weatherDetailsComponent!!
    }

    fun releaseWeatherDetailsComponent() {
        weatherDetailsComponent = null
    }
}