package com.sasaj.graphics.drawingapp.di

import com.sasaj.weatherapp.citieslist.di.WeatherListModule
import com.sasaj.weatherapp.citieslist.di.WeatherListSubcomponent
import com.sasaj.weatherapp.di.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ApplicationModule::class)
])
interface AppComponent {
    fun plus(weatherListModule: WeatherListModule): WeatherListSubcomponent
}