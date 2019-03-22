package com.sasaj.weatherapp.citieslist.di

import com.sasaj.weatherapp.citieslist.LocationListActivity
import dagger.Subcomponent

@ListScope
@Subcomponent(modules = [WeatherListModule::class])
interface WeatherListSubcomponent {
    fun inject(locationListActivity: LocationListActivity)
}