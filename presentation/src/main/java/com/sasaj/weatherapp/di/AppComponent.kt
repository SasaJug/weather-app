package com.sasaj.graphics.drawingapp.di

import com.sasaj.weatherapp.cities.di.WeatherListModule
import com.sasaj.weatherapp.cities.di.WeatherListSubcomponent
import com.sasaj.weatherapp.citydetails.di.WeatherDetailsModule
import com.sasaj.weatherapp.citydetails.di.WeatherDetailsSubcomponent
import com.sasaj.weatherapp.di.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ApplicationModule::class)
])
interface AppComponent {
    fun plus(weatherListModule: WeatherListModule): WeatherListSubcomponent
    fun plus(weatherDetailsModule: WeatherDetailsModule): WeatherDetailsSubcomponent
}