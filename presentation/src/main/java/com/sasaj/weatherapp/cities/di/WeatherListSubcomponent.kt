package com.sasaj.weatherapp.cities.di

import com.sasaj.weatherapp.cities.LocationListActivity
import dagger.Subcomponent

@ListScope
@Subcomponent(modules = [WeatherListModule::class])
interface WeatherListSubcomponent {
    fun inject(locationListActivity: LocationListActivity)
}