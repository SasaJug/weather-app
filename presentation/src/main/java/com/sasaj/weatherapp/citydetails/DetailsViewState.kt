package com.sasaj.weatherapp.citydetails

import com.sasaj.domain.entities.Weather

data class DetailsViewState(var state: Int = -1, var weather: Weather? = null) {
    companion object {
        const val LOADING: Int = 0
        const val WEATHER_LOADED: Int = 1
    }
}