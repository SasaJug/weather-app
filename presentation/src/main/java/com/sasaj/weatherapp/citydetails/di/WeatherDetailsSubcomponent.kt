package com.sasaj.weatherapp.citydetails.di

import com.sasaj.weatherapp.citydetails.LocationDetailFragment
import dagger.Subcomponent

@DetailsScope
@Subcomponent(modules = [WeatherDetailsModule::class])
interface WeatherDetailsSubcomponent {
    fun inject(locationDetailFragment: LocationDetailFragment)
}